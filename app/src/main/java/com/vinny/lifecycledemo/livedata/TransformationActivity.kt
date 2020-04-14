package com.vinny.lifecycledemo.livedata

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import com.vinny.lifecycledemo.R
import com.vinny.lifecycledemo.entity.ClothEntity
import com.vinny.lifecycledemo.entity.UserEntity
import kotlinx.android.synthetic.main.activity_transformation_livedata.*

/**
 * Description : TransformationActivity LiveData的Transformation转换(map/switchmap)
 * Created : CGG
 * Time : 2020/4/13
 * Version : 0.0.1
 */
class TransformationActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformation_livedata)

        /****************************map的使用********************************/
        //创建ViewModel
        val viewModel = ViewModelProvider(this).get(TransformationViewModel::class.java)
        // 创建观察者
        val userObserver = Observer<String>() {
            tv_data.text = "map:$it"
        }
        //建立订阅关系
        viewModel.getLiveData().observe(this, userObserver)

        btn_map_livedata.setOnClickListener {
            viewModel.getUserInfo()
        }


        /*************************switchmap的使用*******************************/
        // 创建观察者，观察服装信息
        val clothObserver = Observer<ClothEntity>() {
            tv_data.text = "switchmap:${it.clothName}"
        }
        // 建立订阅关系
        viewModel.getClothLiveData().observe(this, clothObserver)

        btn_switchmap_livedata.setOnClickListener {
            val clothId = "128931234910"
            viewModel.getClothInfo(clothId)
        }
    }

}