package com.vinny.lifecycledemo.observer

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

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
    fun onResumeListener() {
        Log.e(TAG, "===onResume===")
    }

    /**
     * ON_PAUSE:监听onPause生命周期事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseListener() {
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