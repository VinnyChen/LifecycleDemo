package com.vinny.lifecycledemo.utils

/**
 * Description : RobTicketManager 抢票管理
 * Created : CGG
 * Time : 2020/4/10
 * Version : 0.0.1
 */
class RobTicketManager {

    /**
     * 开始监听余票信息
     */
    fun startUpdateTickets(listener: TicketListener) {
        print("开始监听余票信息")
        // 当监听到有余票
        listener.onSurplusTicketChanged(2)
    }

    /**
     * 停止监听余票信息
     */
    fun stopUpdateTickets(listener: TicketListener) {
        print("停止监听余票信息")
    }

}

interface TicketListener {
    /**
     * 余票数量变动时
     */
    fun onSurplusTicketChanged(num: Int) {

    }
}