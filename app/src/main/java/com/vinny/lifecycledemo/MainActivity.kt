package com.vinny.lifecycledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vinny.lifecycledemo.lifecycle.LifecycleUsedActivity
import com.vinny.lifecycledemo.lifecycle.LifecycleUsedAppCompatActivity
import com.vinny.lifecycledemo.livedata.LiveDataActivity
import com.vinny.lifecycledemo.livedata.LiveDataViewModelActivity
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
            //startActivity(Intent(this, LiveDataActivity::class.java))
            startActivity(Intent(this, LiveDataViewModelActivity::class.java))
        }
    }

}
