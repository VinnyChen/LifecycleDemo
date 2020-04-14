# LifecycleDemo
## 一、干什么的？
> LiveData是一种具有生命感知能力的，可观察的数据存储器类，通常用于ViewModel向界面控制器通信

+ 具有生命感知能力？
    + 指遵循其它组件的生命周期（Activity/Fragment等） 
+ 可观察的数据存储类？
    + 内部存储有一个可观察的Data对象，作为被观察者，一旦感知到变化，会通知观察者
+ 通信？
    + 创建一个观察者Observer，与LiveData达成订阅关系，可实现通信   


## 二、优势在哪儿？
从LiveData的来源中可以知道优势：
+ 不再需要手动处理生命周期，如Presenter
+ 因为可以感知组件生命周期，可以避免内存泄漏，因为LiveData只更新处于生命周期活跃的组件观察者
+ 始终展示最新数据，非活跃状态切换到活跃状态会接收最新数据
+ 配置更改（如：屏幕旋转）也不会影响数据的更新
+ 共享资源，事件总线（不建议使用）

## 三、如何使用
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

## 四、扩展LiveData
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
## 五、相关接口和类
### MutableLiveData
+ 继承自LiveData
+ 提供改变容器内容的接口（setValue()/postValue()）

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

## 六、LiveData的转换
+ Transformations.map() 对value值的更改
+ Transformations.switchMap() 对LiveData的转换

**Transformations.map()**
+ 当需要对LiveData其中的值进行更改时，可以使用map转换 
+ map转换之后，会将结果分派到观察中（map转换也是一个订阅的过程，由map默认实现了）
+ 参数一为源LiveData，参数二为一个方法，返回修改的值（针对其中某一个或多个属性值）
+ 使用map可以确保LiveData的数据不会传递给处于死亡状态的ViewModel和View

viewModel中：
```
class TransformationViewModel : ViewModel() {

    private val userLiveData: MutableLiveData<UserEntity> = MutableLiveData()

    // 当我只需要UserEntity中的nickName和age的时候，可以使用map提取出来，并将结果分派出来
    // Transformations.map中会创建一个观察者观察userLiveData
    // Transformations.map中会创建一个MediatorLiveData，用来接收转换后的LiveData
    // Transformations.map中创建的观察者中会将转换后的值setValue给新创建的MediatorLiveData，再根据订阅关系传出去
    private val nameLiveData: LiveData<String> = Transformations.map(userLiveData) {
        "名字：${it.nickName}--年龄：${it.age}"
    }

    fun getLiveData(): LiveData<String> {
        return nameLiveData
    }

    fun setUserInfo() {
        // 获取数据
        val entity = UserEntity(nickName = "张三", age = "24",userId = "111111222222")
        // 修改转换前的LiveData中的值,因为该LiveData存在map转换，所以会触发map中默认实现的观察者收到事件
        userLiveData.value = entity
    }

}
```

View中：
```
class TransformationActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformation_livedata)
        //创建ViewModel
        val viewModel = ViewModelProvider(this).get(TransformationViewModel::class.java)
        // 创建观察者
        val userObserver = Observer<String>() {
            tv_data.text = it
        }
        //建立订阅关系
        viewModel.getLiveData().observe(this, userObserver)

        btn_map_livedata.setOnClickListener {
            viewModel.setUserInfo()
        }
    }

}
```
总结：
+ 当我们获取数据后，若只需要提取部分数据，或者需要更改其中的某些数据，可以通过map来实现。
+ 如上所示，获取到的是一个UserEntity实体，但我们只需要其中的nickName值，于是通过map转换将UserEntity转换一次得到我们所需要的的值
+ 完整流程
    + 两层订阅关系
        + userObserver与 nameLiveData达成订阅关系
        + userLiveData与map函数中实现的Observer观察者达成订阅关系
    + 流转关系
        + 需要哪一个数据，就观察哪一个LiveData，此处需要nickName，所以直接观察 nameLiveData
        + viewModel.setUserInfo()中造了一个假数据，实际上应该是获取数据，获取数据之后，修改userLiveData的value值
    + map具体实现
        + map函数有两个参数，参数一为userLiveData（即需要转换的LiveData），参数二为一个方法，返回具体的变化值，如上面取出的名字和年龄：【${it.nickName}--年龄：${it.age}"】
        + 内部实例化一个名为result的MediatorLiveData，存储经过转换后的数据
        + 内部实现一个观察者（假设名称为mObserver），用来与通过map传进来的userLiveData达成订阅关系
        + map最后会将result返回，给nameLiveData
        + 当mObserver观察到userLiveData中的value有变化的时候，回调mObserver的onChange方法，在该方法中，会执行map函数的第二参数（一个方法），并将该方法的返回值赋值给result的value
        + userObserver检测到nameLiveData的value发生了变化，更新UI

**Transformations.switchMap()**
viewModel中：
```
class TransformationViewModel : ViewModel() {

    /******************************************switchmap********************************************/
    private val clothRepository = ClothRepository()
    private val clothIdLiveData = MutableLiveData<String>()
    // switchMap(参数一，参数二)
    // 参数一：源LiveData，需要被转化的LiveData
    // 参数二：是一个方法，该方法返回一个LiveData
    private val clothInfoLiveData = Transformations.switchMap(clothIdLiveData) {
        clothRepository.getClothInfoById(it)
    }

    /**
     * 获取服装信息
     */
    fun getClothInfo(clothId: String) {
    // 每次clothId发生变化，都会调用clothRepository.getClothInfoById(clothId)
        clothIdLiveData.value = clothId
    }

    /**
     * 获取clothLiveData
     */
    fun getClothLiveData(): LiveData<ClothEntity> {
        return clothInfoLiveData
    }

}
```
Repository中：
```
class ClothRepository {

    fun getClothInfoById(clothId:String): MutableLiveData<ClothEntity> {
        // 通过userId向网络或者本地获取数据
        // 造假数据
        val userLiveData = MutableLiveData<ClothEntity>()
        val entity = ClothEntity()
        entity.clothId = clothId
        entity.clothName = "ADDS"
        entity.price = 198.9
        userLiveData.value = entity
        return userLiveData
    }

}
```
View中：
```
class TransformationActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformation_livedata)

        //创建ViewModel
        val viewModel = ViewModelProvider(this).get(TransformationViewModel::class.java)

        /*************************switchmap的使用*******************************/
        // 创建观察者，观察服装信息
        val clothObserver = Observer<ClothEntity>() {
            tv_data.text = "switchmap:${it.clothName}"
        }
        // 建立订阅关系
        viewModel.getClothLiveData().observe(this, clothObserver)

        btn_switchmap_livedata.setOnClickListener {
            val clothId = "128931234910"
            viewModel.getClothInfo(clothId)
        }
    }

}
```
总结：
+ 当我们在观察多个LiveData时，想要手动控制切换只监听其中一个LiveData，可以使用switchmap
+ switchmap(参数一，参数二)，参数一为转换前的LiveData，参数二为一个方法，方法返回一个新的LiveData
+ 如上代码所示：我们需要获取的是clothEntity，而这个clothEntity是通过clothId去获取的，于是我们可以监听clothId，当观察到clothId发生变化时，去调用clothRepository.getClothInfoById(clothId)获取clothEntity
+ 完整流程
    + 两层订阅关系
        +  clothObserver与clothInfoLiveData达成订阅关系
        +  clothIdLiveData与与switchmap中的观察者达成订阅关系
    + switchmap中
        + 参数一名为：sourceLiveData（即传进来的clothIdLiveData），参数二为：func 
        + 实例化一个MediatorLiveData名为result
            + 用于添加LiveData
            + 用来作为转换后的返回LiveData
        +  将sourceLiveData添加到result中，达成一个订阅关系（sourceLiveData与result）
            + 当观察到sourceLiveData中的数据发生变化，观察者在onChange中执行func方法，得到一个newLiveData
            + 将newLiveData添加到result中，达成订阅关系（newLiveData与result）
            + 当观察到newLiveData中的数据发生变化时，观察者在onChange中更改result的value值，往下传递，通知View层
            + 在将newLiveData添加到result之前，其实有一个变量来临时存储newLiveData，对比两次数据是否一致，且不同的时候，需要移除上一次的newLiveData
        + 返回result


## 七、应用场景
本质：都是需要在组件不同生命周期做相应操作的
+ MVP中的Presenter
+ MediaPlayer
+ 开始和停止缓冲视频
+ 开始和停止网络连接暂停和恢复动画可绘制资源

## 八、源码解读