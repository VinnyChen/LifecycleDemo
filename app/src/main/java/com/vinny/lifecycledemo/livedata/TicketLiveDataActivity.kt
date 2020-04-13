package com.vinny.lifecycledemo.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vinny.lifecycledemo.R
import kotlinx.android.synthetic.main.activity_ticket_livedata.*

/**
 * Description : SpreadLiveDataActivity 扩展LiveData,监听网络状态
 * Created : CGG
 * Time : 2020/4/9
 * Version : 0.0.1
 */
class TicketLiveDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_livedata)
        //创建ViewModel
        val viewModel = ViewModelProvider(this).get(TicketViewModel::class.java)
        // 创建观察者
        val userObserver = Observer<String>() {
            tv_data.text = it
        }
        //建立订阅关系
        viewModel.getLiveData().observe(this, userObserver)

    }

}