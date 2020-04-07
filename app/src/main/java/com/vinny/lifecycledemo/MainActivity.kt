package com.vinny.lifecycledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vinny.lifecycledemo.lifecycle.LifecycleUsedActivity
import com.vinny.lifecycledemo.lifecycle.LifecycleUsedAppCompatActivity
import com.vinny.lifecycledemo.observer.MyObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initClickListener()
        // lifecycle：管理Activity和Fragment的生命周期
        // 注册监听
        //lifecycle.addObserver(MyObserver())
    }

    private fun initClickListener() {
        btn_lifecycle.setOnClickListener {
            startActivity(Intent(this,LifecycleUsedActivity::class.java))
            //startActivity(Intent(this,LifecycleUsedAppCompatActivity::class.java))
        }
    }

    /**
     * lifecycle使用
     */
    private fun onlifecycleClick(view: View){
        startActivity(Intent(this,LifecycleUsedActivity::class.java))
        //startActivity(Intent(this,LifecycleUsedAppCompatActivity::class.java))
    }

}
