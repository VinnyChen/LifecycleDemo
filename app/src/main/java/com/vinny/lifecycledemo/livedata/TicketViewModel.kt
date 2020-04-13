package com.vinny.lifecycledemo.livedata

import androidx.lifecycle.ViewModel

/**
 * Description : TicketViewModel 余票监测ViewModel
 * Created : CGG
 * Time : 2020/4/9
 * Version : 0.0.1
 */
class TicketViewModel : ViewModel() {

    private val ticketLiveData: TicketLiveData = TicketLiveData()

    fun getLiveData(): TicketLiveData {
        return ticketLiveData
    }


}