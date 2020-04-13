# LifecycleDemo
## 一、干什么的？
> LiveData是一种具有生命感知能力的，可观察的数据存储器类，通常用于ViewModel向界面控制器通信

+ 具有生命感知能力？
    + 指遵循其它组件的生命周期（Activity/Fragment等） 
+ 可观察的数据存储类？
    + 内部存储有一个可观察的Data对象，作为被观察者，一旦感知到变化，会通知观察者
+ 通信？
    + 创建一个观察者Observer，与LiveData达成订阅关系，可实现通信   
## 二、怎么来的？
    
+ 普通的被观察者无法感知应用组件的生命周期，会导致一些不必要的操作，进而产生内存泄漏甚至直接发生崩溃；而LiveData可以感知应用组件的生命周期，即可以根据应用组件的生命周期来决定是否更新UI
+ activity/Fragment中，需要在生命周期中做的操作很多，比如，MVP中presenter的注册与释放，MediaPlayer的注册与释放等等，这些可能根据组件生命周期变化的操作管理起来很麻烦，于是LiveData就应运而生，通过LiveData就不需要相关组件的生命周期都去操作一次

## 三、优势在哪儿？
从LiveData的来源中可以知道优势：
+ 不再需要手动处理生命周期，如Presenter
+ 因为可以感知组件生命周期，可以避免内存泄漏，因为LiveData只更新处于生命周期活跃的组件观察者
+ 始终展示最新数据，非活跃状态切换到活跃状态会接收最新数据
+ 配置更改（如：屏幕旋转）也不会影响数据的更新
+ 共享资源，事件总线（不建议使用）

## 四、相关类介绍
**MutableLiveData：**
+ 继承自LiveData
+ 提供改变容器内容的接口（setValue()/postValue()）

**MediatorLiveData：**
+ 继承自MutableLiveData
+ 可以同时监听多个LiveData的变化，通过addSource()添加LiveData，以达到同时监听多个LiveData
+ 
## 五、如何使用
#### **单独使用：**
1. 创建LiveData实例
 ```
 // 第一步：创建LiveData实例
 val liveData = MutableLiveData<String>()
 ```
2. 创建观察者对象observer
```
// 第二步：创建observer
val observer = Observer<String>() { value ->
    tv_data.text = value
}
```
3. 注册/订阅
```
// 第三步，注册，订阅，关联observer和liveData
// 参数一为LifecycleOwner，AppCompatActivity默认已经实现,否则需要自己实现LifecycleOwner
liveData.observe(this,observer)
```
4. 更新数据(setValue()/postValue()
```
//第四步：修改数据，更新UI
liveData.value = "修改成功"
```

#### **通常与ViewModel连用：**
1. 创建MyViewModel类继承自ViewModel
+ 在MyViewModel中创建LiveData
+ 对外提供一个get方法获取LiveData实例
```
class MyViewModel: ViewModel() {

    private var myLiveData:MutableLiveData<String> = MutableLiveData()

    fun getMyLiveData(): MutableLiveData<String> {
        return myLiveData
    }

}
```
2. 创建MyViewModel实例
```
myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
```
3. 创建观察者对象
```
val nameObserver = Observer<String>(){ value ->
    tv_data.text = value
}
```
4. 注册/订阅
```
myViewModel.getMyLiveData().observe(this,nameObserver)
```
5. 更新数据
```
myViewModel.getMyLiveData().value = "修改成功"
```

## 六、扩展LiveData
有的时候，我们想要在onInActive()或者onActive()中做一些别的操作（如注册与取消广播），此时我们就需要对LiveData进行扩展,通过继承LiveData或者MutableLiveData实现扩展

**TicketLiveData：**

```
class TicketLiveData : LiveData<String>() {

    private var ticketManager: RobTicketManager = RobTicketManager()

    private val listener = object : TicketListener {
        override fun onSurplusTicketChanged(num:Int) {
            super.onSurplusTicketChanged(num)
            if(num > 0){
                value = "开始抢票了..."
            }
        }
    }


    override fun onActive() {
        super.onActive()
        ticketManager.startUpdateTickets(listener)
    }

    override fun onInactive() {
        super.onInactive()
        ticketManager.stopUpdateTickets(listener)
    }

}
```
**RobTicketManager：**
```
class RobTicketManager {

    /**
     * 开始监听余票信息
     */
    fun startUpdateTickets(listener: TicketListener) {
        print("开始监听余票信息")
        // 当监听到有余票
        listener.onSurplusTicketChanged(2)
    }

    /**
     * 停止监听余票信息
     */
    fun stopUpdateTickets(listener: TicketListener) {
        print("停止监听余票信息")
    }

}

interface TicketListener {
    /**
     * 余票数量变动时
     */
    fun onSurplusTicketChanged(num: Int) {

    }
}
```
**TicketLiveData :**
```
class TicketLiveData : LiveData<String>() {

    private var ticketManager: RobTicketManager = RobTicketManager()

    private val listener = object : TicketListener {
        override fun onSurplusTicketChanged(num:Int) {
            super.onSurplusTicketChanged(num)
            if(num > 0){
                value = "开始抢票了..."
            }
        }
    }


    override fun onActive() {
        super.onActive()
        ticketManager.startUpdateTickets(listener)
    }

    override fun onInactive() {
        super.onInactive()
        ticketManager.stopUpdateTickets(listener)
    }

}
```

**订阅：**
```
class TicketLiveDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_livedata)
        //创建ViewModel
        val viewModel = ViewModelProvider(this).get(TicketViewModel::class.java)
        // 创建观察者
        val userObserver = Observer<String>() {
            tv_data.text = it
        }
        //建立订阅关系
        viewModel.getLiveData().observe(this, userObserver)

    }

}
```
## 相关接口和类

### MediatorLiveData的使用
+ 继承自MutableLiveData
+ 可监听多个LiveData的内容改变
+ 通过addSource()添加LiveData，来监听多个LiveData
想添加多个，又只监听一个，则使用Transformations.switchmap（注意：若View处于InActive状态，此时，多个数据源都发生变化，当恢复至active状态时，只会获取最后添加（addSource）的LiveData的数据）

下面看看具体的使用：

**ViewModel:**
```
class MediatorViewModel : ViewModel() {

    private var strLiveData: MutableLiveData<String> = MutableLiveData()
    private var userLiveData: MutableLiveData<UserEntity> = MutableLiveData()

    private var mediatorLiveData: MediatorLiveData<String> = MediatorLiveData()

    init {
        mediatorLiveData.addSource(strLiveData){
            mediatorLiveData.value = "strLiveData:$it"
        }
        mediatorLiveData.addSource(userLiveData){
            mediatorLiveData.value = "userLiveData:${it.nickName}"
        }
    }

    fun getLiveData(): MediatorLiveData<String> {
        return mediatorLiveData
    }

    fun changeStr(){
        strLiveData.value = "改变字符串"
    }

    fun changeUser(){
        val entity = UserEntity(nickName = "测试名称")
        userLiveData.value = entity
    }

}
```

**Activity:**
```
class MediatorLiveDataActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediator_livedata)
        //创建ViewModel
        val viewModel = ViewModelProvider(this).get(MediatorViewModel::class.java)
        // 创建观察者
        val userObserver = Observer<String>() {
            tv_data.text = it
        }
        //建立订阅关系
        viewModel.getLiveData().observe(this, userObserver)

        btn_str_livedata.setOnClickListener {
            viewModel.changeStr()
        }

        btn_user_livedata.setOnClickListener {
            viewModel.changeUser()
        }

    }

}
```

## LiveData的转换
+ Transformations.map()
+ Transformations.switchMap()

## 七、应用场景
本质：都是需要在组件不同生命周期做相应操作的
+ MVP中的Presenter
+ MediaPlayer
+ 开始和停止缓冲视频
+ 开始和停止网络连接暂停和恢复动画可绘制资源

## 七、源码解读