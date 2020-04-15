package com.vinny.lifecycledemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vinny.lifecycledemo.lifecycle.LifecycleUsedActivity
import com.vinny.lifecycledemo.viewmodel.ShareViewModelActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initClickListener()
    }

    private fun initClickListener() {
        btn_lifecycle.setOnClickListener {
            startActivity(Intent(this, LifecycleUsedActivity::class.java))
            //startActivity(Intent(this,LifecycleUsedAppCompatActivity::class.java))
        }

        btn_livedata.setOnClickListener {
            // 跳转到LiveData的使用
            //startActivity(Intent(this, LiveDataActivity::class.java))

            // 跳转到LiveData与ViewModel连用
            //startActivity(Intent(this, LiveDataViewModelActivity::class.java))

            // 跳转到LiveData的扩展
            //startActivity(Intent(this, TicketLiveDataActivity::class.java))

            // 跳转到MediatorLiveData的使用
            //startActivity(Intent(this, MediatorLiveDataActivity::class.java))

            // 跳转到map/switchmap的使用介绍
            //startActivity(Intent(this, TransformationActivity::class.java))

            // 跳转到ViewModel的使用介绍
            //startActivity(Intent(this, ViewModelActivity::class.java))

            // 跳转到ViewModel在同一个Activity下的Fragment之间共享数据的使用介绍
            startActivity(Intent(this, ShareViewModelActivity::class.java))
        }
    }

}
