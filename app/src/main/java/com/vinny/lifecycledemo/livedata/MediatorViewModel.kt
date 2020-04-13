package com.vinny.lifecycledemo.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinny.lifecycledemo.entity.UserEntity

/**
 * Description : MediatorViewModel
 * Created : CGG
 * Time : 2020/4/7
 * Version : 0.0.1
 */
class MediatorViewModel : ViewModel() {

    private var strLiveData: MutableLiveData<String> = MutableLiveData()
    private var userLiveData: MutableLiveData<UserEntity> = MutableLiveData()

    private var mediatorLiveData: MediatorLiveData<String> = MediatorLiveData()

    init {
        mediatorLiveData.addSource(strLiveData){
            mediatorLiveData.value = "strLiveData:$it"
        }
        mediatorLiveData.addSource(userLiveData){
            mediatorLiveData.value = "userLiveData:${it.nickName}"
        }
    }

    fun getLiveData(): MediatorLiveData<String> {
        return mediatorLiveData
    }

    fun changeStr(){
        strLiveData.value = "改变字符串"
    }

    fun changeUser(){
        val entity = UserEntity(nickName = "测试名称")
        userLiveData.value = entity
    }

}