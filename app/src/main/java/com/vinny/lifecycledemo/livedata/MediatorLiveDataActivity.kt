package com.vinny.lifecycledemo.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vinny.lifecycledemo.R
import kotlinx.android.synthetic.main.activity_mediator_livedata.*

/**
 * Description : MediatorLiveDataActivity MediatorLiveData的使用
 * Created : CGG
 * Time : 2020/4/13
 * Version : 0.0.1
 */
class MediatorLiveDataActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediator_livedata)
        //创建ViewModel
        val viewModel = ViewModelProvider(this).get(MediatorViewModel::class.java)
        // 创建观察者
        val userObserver = Observer<String>() {
            tv_data.text = it
        }
        //建立订阅关系
        viewModel.getLiveData().observe(this, userObserver)


        btn_str_livedata.setOnClickListener {
            viewModel.changeStr()
        }

        btn_user_livedata.setOnClickListener {
            viewModel.changeUser()
        }

    }

}