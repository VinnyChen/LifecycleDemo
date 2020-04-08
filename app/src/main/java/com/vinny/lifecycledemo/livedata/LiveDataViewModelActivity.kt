package com.vinny.lifecycledemo.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vinny.lifecycledemo.R
import kotlinx.android.synthetic.main.activity_livedata.*

/**
 * Description : LiveDataViewModelActivity LiveData与ViewModel连用
 * Created : CGG
 * Time : 2020/4/7
 * Version : 0.0.1
 */
class LiveDataViewModelActivity:AppCompatActivity() {

    private lateinit var myViewModel:MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        val nameObserver = Observer<String>(){ value ->
            tv_data.text = value
        }

        myViewModel.getMyLiveData().observe(this,nameObserver)

        btn_update.setOnClickListener {
            myViewModel.getMyLiveData().value = "修改成功"
        }
    }

}