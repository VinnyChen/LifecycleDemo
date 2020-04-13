package com.vinny.lifecycledemo.entity

/**
 * Description : Resource 包装接口返回体
 * Created : CGG
 * Time : 2020/4/9
 * Version : 0.0.1
 */
class Resource<T>(
    /**
     * 错误码
     */
    val code: String? = null,
    /**
     * 错误信息
     */
    val message: String? = null,
    /**
     * 实体
     */
    val data:T? = null
)