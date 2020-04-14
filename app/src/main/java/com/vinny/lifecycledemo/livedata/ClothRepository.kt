package com.vinny.lifecycledemo.livedata

import androidx.lifecycle.MutableLiveData
import com.vinny.lifecycledemo.entity.ClothEntity
import com.vinny.lifecycledemo.entity.UserEntity

/**
 * Description : UserRepository 服装数据仓库,获取数据的中介
 * Created : CGG
 * Time : 2020/4/14
 * Version : 0.0.1
 */
class ClothRepository {

    fun getClothInfoById(clothId:String): MutableLiveData<ClothEntity> {
        // 通过userId向网络或者本地获取数据
        // 造假数据
        val userLiveData = MutableLiveData<ClothEntity>()
        val entity = ClothEntity()
        entity.clothId = clothId
        entity.clothName = "ADDS"
        entity.price = 198.9
        userLiveData.value = entity
        return userLiveData
    }

}