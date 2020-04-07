# LifecycleDemo
Lifecycle框架详解

## 一、简介
> Lifecycle是一个生命周期感知组件，可以感知Activity和Fragment等组件的生命周期，并且将感知到的变化通知观察者LifecycleObserver，减少内存泄漏的可能，增强代码的稳定性

$\color{#FF0000}{2}$

## 二、几个重要的类和接口
**lifecycle：**


+ 抽象类
+ 定义了订阅方法 **addObserver()**,取消订阅方法 **removeObserver()** 等
+ 定义了获取当前状态的方法 **getCurrentState()**
+ 定义了Event枚举和State枚举
+ 被观察者

**LifecycleOwner**
+ 接口，Lifecycle的持有者
+ 生命周期的提供者
+ 定义了获取Lifecycle的抽象方法
+ 需要获取Lifecycle的类都需要实现该方法

**LifecycleObserver**
+ 接口，其中没有定义方法
+ 生命周期的观察者，通过@OnLifecycleEvent注解方法 来接收生命周期事件
+ 开发者可自定义方法，添加注解来接收对应的生命周期事件

**LifecycleRegistry**
+ Lifecycle的子类，getLifecycle()实质获取的实例
+ 真正的被观察者

**Event**
+ 生命周期事件，映射Activity/Fragment中的生命周期
+ 包含：**ON_CREATE**、**ON_START**、**ON_RESUME**、**ON_PAUSE**、**ON_STOP**、**ON_DESTROY**、**ON_ANY**（可以接收所有生命周期事件）
+ 当state发生变化时，会向已经注册的LifecycleObserver发送事件

**State**
+  生命周期状态
    + **DESTROYED** ondestory的执行
    + **INITIALIZED** 被构造之后，onCreate执行之前
    + **CREATED** onCreate，onStop的执行
    + **STARTED** onStart，onPause的执行
    + **RESUMED** onResume的执行
## 三、使用步骤
### 1.自定义一个类MyObserver实现LifecycleObserver

```
/**
 * Description : MyObserver 观察者，用来监听Activity或者Fragment的生命周期
 * Created : CGG
 * Time : 2020/4/2
 * Version : 0.0.1
 */
class MyObserver : LifecycleObserver {
    /**
     * LifecycleObserver:生命周期观察者接口
     */

    private val TAG = "MyObserver"

    /**
     * onCreate 方法名称自定义
     * 注解参数： 需要监听的是什么生命周期事件
     * ON_CREATE:接收onCreate生命周期的事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.d(TAG, "===onCreate===")
    }

    /**
     *ON_START:监听onStart生命周期事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.d(TAG, "===onCreated===")
    }

    /**
     * onResume:监听onResume生命周期事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.e(TAG, "===onResume===")
    }

    /**
     * ON_PAUSE:监听onPause生命周期事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.e(TAG, "===onPause===")
    }

    /**
     * ON_STOP:监听onStop生命周期事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.d(TAG, "===onStop===")
    }

    /**
     * ON_ANY:可接收任意生命周期的事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny() {
        Log.d(TAG, "===onAny===")
    }

}
```

### 2.获取Lifecycle(实际上是获取LifecycleRegistry)

+ 如果继承的是AppCompatActivity，且版本大于等于26.1.0
    + 可直接获取lifecycle， 因为AppCompatActivity中已经默认实现
```
class LifecycleUsedAppCompatActivity:AppCompatActivity() {

    private lateinit var mLifecycle: Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_use_appcompat)
        // lifecycle：管理Activity和Fragment的生命周期
        // 1.获取lifecycle:
        mLifecycle = lifecycle
        // 2.注册监听
        lifecycle.addObserver(MyObserver())
    }

}
```
+ Activity中，或者AppCompatActivity中且版本小于26.1.0
    + 则需要自己实现LifecycleOwner接口
    + 并且，事件的分发也需要自己去实现，如下：
```
/**
 * Description : LifecycleUsedActivity lifecycle适用于继承自Activity中
 * Created : CGG
 * Time : 2020/4/7
 * Version : 0.0.1
 */
class LifecycleUsedActivity:Activity(),LifecycleOwner {

    private lateinit var mLifecycleRegistry:LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1.创建LifecycleRegistry
        mLifecycleRegistry = LifecycleRegistry(this)
        // 2.处理生命周期事件
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        // 3.注册监听
        mLifecycleRegistry.addObserver(MyObserver())
    }

    override fun onStart() {
        super.onStart()
        // 2.处理生命周期事件
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }
}
```

### 3.注册，订阅

```
mLifecycle.addObserver(MyObserver())
```
或者
```
mLifecycleRegistry.addObserver(MyObserver())
```

**运行查看结果：**
+ AppCompatActivity中：
```
2020-04-07 11:52:18.773 8551-8551/com.vinny.lifecycledemo D/MyObserver: ===onCreate===
2020-04-07 11:52:18.773 8551-8551/com.vinny.lifecycledemo D/MyObserver: ===onAny===
2020-04-07 11:52:18.782 8551-8551/com.vinny.lifecycledemo D/MyObserver: ===onStart===
2020-04-07 11:52:18.782 8551-8551/com.vinny.lifecycledemo D/MyObserver: ===onAny===
2020-04-07 11:52:18.785 8551-8551/com.vinny.lifecycledemo D/MyObserver: ===onResume===
2020-04-07 11:52:18.785 8551-8551/com.vinny.lifecycledemo D/MyObserver: ===onAny===
2020-04-07 11:52:19.046 8551-8551/com.vinny.lifecycledemo D/MyObserver: ===onStop===
2020-04-07 11:52:19.046 8551-8551/com.vinny.lifecycledemo D/MyObserver: ===onAny===
```
+ Activity中：
```
2020-04-07 11:57:37.261 9281-9281/com.vinny.lifecycledemo D/MyObserver: ===onCreate===
2020-04-07 11:57:37.261 9281-9281/com.vinny.lifecycledemo D/MyObserver: ===onAny===
2020-04-07 11:57:37.289 9281-9281/com.vinny.lifecycledemo D/MyObserver: ===onStart===
2020-04-07 11:57:37.289 9281-9281/com.vinny.lifecycledemo D/MyObserver: ===onAny===
```

由以上结果我们可以看出：
1. Activity的生命周期与lifecycle已经关联起来了
2. onAny可以接收所有生命周期的事件


最后，

[代码直通车](https://github.com/VinnyChen/LifecycleDemo)

**注意：Tag切换到v1.0**
