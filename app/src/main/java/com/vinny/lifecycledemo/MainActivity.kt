package com.vinny.lifecycledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinny.lifecycledemo.lifecycle.LifecycleUsedActivity
import com.vinny.lifecycledemo.livedata.MediatorLiveDataActivity
import com.vinny.lifecycledemo.livedata.TicketLiveDataActivity
import com.vinny.lifecycledemo.livedata.TransformationActivity
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
            //startActivity(Intent(this, LiveDataViewModelActivity::class.java))
            //startActivity(Intent(this, TicketLiveDataActivity::class.java))
            //startActivity(Intent(this, MediatorLiveDataActivity::class.java))
            startActivity(Intent(this, TransformationActivity::class.java))
        }
    }

}
