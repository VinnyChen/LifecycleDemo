package com.vinny.lifecycledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import com.vinny.lifecycledemo.observer.MyObserver

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // lifecycle：管理Activity和Fragment的生命周期
        // 注册监听
        lifecycle.addObserver(MyObserver())
    }
}
