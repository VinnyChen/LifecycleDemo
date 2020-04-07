package com.vinny.lifecycledemo.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.vinny.lifecycledemo.R
import com.vinny.lifecycledemo.observer.MyObserver

/**
 * Description : LifecycleUsedAppCompatActivity lifecycle在AppCompatActivity中使用
 * Created : CGG
 * Time : 2020/4/7
 * Version : 0.0.1
 */
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