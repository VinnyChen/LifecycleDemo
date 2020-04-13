package com.vinny.lifecycledemo.livedata

import androidx.lifecycle.LiveData
import com.vinny.lifecycledemo.utils.RobTicketManager
import com.vinny.lifecycledemo.utils.TicketListener

/**
 * Description : TicketLiveData
 * Created : CGG
 * Time : 2020/4/10
 * Version : 0.0.1
 */
class TicketLiveData : LiveData<String>() {

    private var ticketManager: RobTicketManager = RobTicketManager()

    private val listener = object : TicketListener {
        override fun onSurplusTicketChanged(num:Int) {
            super.onSurplusTicketChanged(num)
            if(num > 0){
                value = "开始抢票了..."
            }
        }
    }


    override fun onActive() {
        super.onActive()
        ticketManager.startUpdateTickets(listener)
    }

    override fun onInactive() {
        super.onInactive()
        ticketManager.stopUpdateTickets(listener)
    }

}