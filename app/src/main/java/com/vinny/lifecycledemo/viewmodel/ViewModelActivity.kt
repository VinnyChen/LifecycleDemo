package com.vinny.lifecycledemo.viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vinny.lifecycledemo.R
import com.vinny.lifecycledemo.entity.UserEntity
import kotlinx.android.synthetic.main.activity_viewmodel.*

/**
 * Description : ViewModelActivity viewmodel使用
 * Created : CGG
 * Time : 2020/4/15
 * Version : 0.0.1
 */
class ViewModelActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel)
        lifecycle
        // 创建ViewModel
        // 如果出现屏幕旋转，导致Activity重绘，重新创建的activity接收的viewModel与之前的实例相同
        // 通过ViewModelProvider(this)来关联当前Activity的生命周期,
        // this表示当前Activity中的lifecycle,ViewModel的生命周期取决于lifecycle
        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // 检测activity生命周期信息,当检测到Activity处于活跃状态时，观察者才会收到消息
        viewModel.getUser().observe(this, Observer<UserEntity> {
            // 更新UI
            tv_data.text = "获取成功：${it.nickName}"
        })

        btn_get_data.setOnClickListener {
            // 获取数据
            viewModel.loadUser()
        }

    }
}