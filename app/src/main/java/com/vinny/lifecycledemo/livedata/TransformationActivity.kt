package com.vinny.lifecycledemo.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import com.vinny.lifecycledemo.R
import com.vinny.lifecycledemo.entity.UserEntity
import kotlinx.android.synthetic.main.activity_transformation_livedata.*

/**
 * Description : TransformationActivity LiveData的Transformation转换
 * Created : CGG
 * Time : 2020/4/13
 * Version : 0.0.1
 */
class TransformationActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformation_livedata)
        //创建ViewModel
        val viewModel = ViewModelProvider(this).get(TransformationViewModel::class.java)
        // 创建观察者
        val userObserver = Observer<String>() {
            tv_data.text = it
        }
        //建立订阅关系
        viewModel.getLiveData().observe(this, userObserver)

        btn_map_livedata.setOnClickListener {
            viewModel.setUserInfo()
        }
    }

}