package com.vinny.lifecycledemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Description : ShareViewModel Fragment共享使用的ViewModel
 * Created : CGG
 * Time : 2020/4/15
 * Version : 0.0.1
 */
class ShareViewModel: ViewModel() {

    private val shareLiveData = MutableLiveData<String>()

    fun getShareLiveData():MutableLiveData<String>{
        return shareLiveData
    }

    /**
     * 发送数据
     */
    fun sendData(data:String){
        shareLiveData.value = data
    }
}