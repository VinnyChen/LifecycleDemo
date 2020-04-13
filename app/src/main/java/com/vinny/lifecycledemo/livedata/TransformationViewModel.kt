package com.vinny.lifecycledemo.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vinny.lifecycledemo.entity.UserEntity

/**
 * Description : TransformationViewModel LiveData转换ViewModel
 * Created : CGG
 * Time : 2020/4/13
 * Version : 0.0.1
 */
class TransformationViewModel : ViewModel() {

    private val userLiveData: MutableLiveData<UserEntity> = MutableLiveData()

    // 当我只需要UserEntity中的nickName和age的时候，可以使用map提取出来，并将结果分派出来
    // Transformations.map中会创建一个观察者观察userLiveData
    // Transformations.map中会创建一个MediatorLiveData，用来接收转换后的LiveData
    // Transformations.map中创建的观察者中会将转换后的值setValue给新创建的MediatorLiveData，再根据订阅关系传出去
    private val nameLiveData: LiveData<String> = Transformations.map(userLiveData) {
        "名字：${it.nickName}--年龄：${it.age}"
    }

    fun getLiveData(): LiveData<String> {
        return nameLiveData
    }

    fun setUserInfo() {
        // 获取数据
        val entity = UserEntity(nickName = "张三", age = "24",userId = "111111222222")
        // 修改转换前的LiveData中的值,因为该LiveData存在map转换，所以会触发map中默认实现的观察者收到事件
        userLiveData.value = entity
    }

}