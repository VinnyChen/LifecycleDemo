package com.vinny.lifecycledemo.lifecycle

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.vinny.lifecycledemo.observer.MyObserver

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