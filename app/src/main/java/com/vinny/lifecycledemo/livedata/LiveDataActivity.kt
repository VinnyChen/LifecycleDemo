package com.vinny.lifecycledemo.livedata

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.vinny.lifecycledemo.R
import kotlinx.android.synthetic.main.activity_livedata.*

/**
 * Description : LiveDataActivity LiveData使用
 * Created : CGG
 * Time : 2020/4/7
 * Version : 0.0.1
 */
class LiveDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata)
        // 第一步：创建LiveData实例
        val liveData = MutableLiveData<String>()
        // 第二步：创建observer
        val observer = Observer<String>() { value ->
            tv_data.text = value
        }
        // 第三步，注册，订阅，关联observer和liveData
        // 参数一为LifecycleOwner，AppCompatActivity默认已经实现,否则需要自己实现LifecycleOwner
        liveData.observe(this,observer)

        btn_update.setOnClickListener {
            //第四步：修改数据，更新UI
            liveData.value = "修改成功"
        }
    }

}