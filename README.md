# LifecycleDemo
Lifecycle框架详解

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
## 六、应用场景
本质：都是需要在组件不同生命周期做相应操作的
+ MVP中的Presenter
+ MediaPlayer
+ 开始和停止缓冲视频
+ 开始和停止网络连接暂停和恢复动画可绘制资源

## 七、源码解读