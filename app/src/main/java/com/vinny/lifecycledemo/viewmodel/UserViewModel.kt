package com.vinny.lifecycledemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinny.lifecycledemo.entity.UserEntity

/**
 * Description : UserViewModel viewModel的使用
 * Created : CGG
 * Time : 2020/4/15
 * Version : 0.0.1
 */
class UserViewModel : ViewModel() {

    private val userLiveData = MutableLiveData<UserEntity>()

    fun getUser(): MutableLiveData<UserEntity> {
        return userLiveData
    }

    /**
     * 加载用户数据
     */
    fun loadUser() {
        //网络获取用户信息
        val entity = UserEntity()
        entity.userId = "1361928375"
        entity.nickName = "viewModel"
        entity.age = "25"
        //将获取的信息赋予userLiveData
        userLiveData.value = entity
    }
}