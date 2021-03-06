# LifecycleDemo
# ViewModel使用详解
## 简介
> 以注重生命周期的方式存储和管理界面相关的数据（只是管理数据，并不生产数据），让数据可在手机配置更改的时候（如屏幕旋转/语言更改等）仍然存在

## 解决了什么问题？
+ 以前因为屏幕旋转丢失的数据，使用了ViewModel将不会再丢失
    + 以前都是使用onSaveInstanceState恢复数据，但此方法只适合少量的数据，如果要保存大量的数据的时候就不太适合了，如列表、表单数据
    + 用户在填写表单的时候，不小心屏幕旋转了无法做到恢复的话用户会崩溃的，ViewModel的出现正好解决了这个问题
+ 解决了异步操作可能出现的内存泄漏，通过onCleared()自己去实现
+ 做到了数据与UI分离
    + View层只需要做UI的渲染，将对数据的处理操作抽取到ViewModel中
+ 数据共享
    + 同一个Activity下的Fragment之间可以相互通信

## 如何使用？

ViewModel：
```
class UserViewModel : ViewModel() {

    private val userLiveData = MutableLiveData<UserEntity>()

    fun getUser(): MutableLiveData<UserEntity> {
        return userLiveData
    }

    /**
     * 加载用户数据
     */
    private fun loadUser() {
        //网络获取用户信息
        val entity = UserEntity()
        entity.userId = "1361928375"
        entity.nickName = "viewModel"
        entity.age = "25"
        //将获取的信息赋予userLiveData
        userLiveData.value = entity
    }
}
```
View:从Activity中访问该数据
```
class ViewModelActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel)
        lifecycle
        // 创建ViewModel
        // 如果出现屏幕旋转，导致Activity重绘，重新创建的activity接收的viewModel与之前的实例相同
        // 通过ViewModelProvider(this)来关联当前Activity的生命周期,
        // this表示当前Activity中的lifecycle,ViewModel的生命周期取决于lifecycle
        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // 检测activity生命周期信息,当检测到Activity处于活跃状态时，观察者才会收到消息
        viewModel.getUser().observe(this, Observer<UserEntity> {
            // 更新UI
            tv_data.text = "获取成功：${it.nickName}"
        })

        btn_get_data.setOnClickListener {
            // 获取数据
            viewModel.loadUser()
        }

    }
}
```

**总结：**
+ ViewModelProvider(this).get(ViewModel类)
    + ViewModel的创建通过此方法，不会因为Activity的重绘而重新创建
    + ViewModel的生命周期也是通过此方法与lifecycle绑定，lifecycle绑定了Activity的生命周期
+ viewModel.getUser().observe(this,观察者)
    + 会监听Activity的生命周期，只有处于活跃状态时，才会接受消息去更新UI 

**注意：**

 ViewModel不能持有Activity的引用，因为ViewModel生命周期比Activity长，持有Activity的引用的话容易引起内存泄漏，如果确实需要这样的引用，则使用AndroidViewModel(application)
 
 ## Fragment之间共享数据
 
 > 注意只能是同一个Activity下的Fragment之间共享数据
 
 先看代码：
 
** ShareViewModel:**
```
class ShareViewModel: ViewModel() {

    private val shareLiveData = MutableLiveData<String>()

    fun getShareLiveData():MutableLiveData<String>{
        return shareLiveData
    }

    /**
     * 发送数据
     */
    fun sendData(data:String){
        shareLiveData.value = data
    }
}
```
** FirstFragment:**

作为发送方发送数据
```
class FirstFragment : Fragment() {

    private lateinit var shareViewModel: ShareViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    private fun initView() {
        //注意：此处ViewModelProvider必须填Activity中的lifecycle，不能传Fragment中的lifecycle（即this）
        shareViewModel = activity?.let {
            ViewModelProvider(it).get(ShareViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        btn_send.setOnClickListener {
            shareViewModel.sendData("数据包")
        }
        shareViewModel.getShareLiveData().observe(this, Observer<String> {
            tv_data.text = "firstFragment\n接收成功"
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

}
```

** SecondFragment:**

作为接收方接收数据
```
class SecondFragment : Fragment() {

    private lateinit var shareViewModel: ShareViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //注意：此处ViewModelProvider必须填Activity中的lifecycle，不能传Fragment中的lifecycle（即this）
        shareViewModel = activity?.let {
            ViewModelProvider(it).get(ShareViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        shareViewModel.getShareLiveData().observe(this, Observer<String> {
            tv_data.text = "secondFragment\n接收成功"
        })
    }
}
```

总结：
+ Activity中不需要做任何的操作，也不需要对此通信有任何的了解
+ 只对同一个Activity下的Fragment共享
+ Fragment之间相互不影响，一个Fragment消失，另一个Fragment仍旧正常工作
+ Fragment都有各自的生命周期，Fragment之间不受别的生命周期的影响
+ 不同的Fragment获取的shareViewModel是同一个
+ 在Fragment中获取shareViewModel的时候,ViewModelProvider()方法中一定传入的是activiy中的lifecycle，而不是Fragment中的lifecycle，因此，传入this是无法共享数据的

