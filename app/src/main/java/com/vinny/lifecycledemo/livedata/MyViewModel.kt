package com.vinny.lifecycledemo.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Description : MyViewModel
 * Created : CGG
 * Time : 2020/4/7
 * Version : 0.0.1
 */
class MyViewModel: ViewModel() {

    private var myLiveData:MutableLiveData<String> = MutableLiveData()

    fun getMyLiveData(): MutableLiveData<String> {
        return myLiveData
    }

}