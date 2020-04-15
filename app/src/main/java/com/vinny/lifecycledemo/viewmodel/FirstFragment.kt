package com.vinny.lifecycledemo.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vinny.lifecycledemo.R
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * Description : FirstFragment
 * Created : CGG
 * Time : 2020/4/15
 * Version : 0.0.1
 */
class FirstFragment : Fragment() {

    companion object {
        fun getInstance(): FirstFragment {
            return FirstFragment()
        }
    }

    private lateinit var shareViewModel: ShareViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    private fun initView() {
        //注意：此处ViewModelProvider必须填Activity中的lifecycle，不能传Fragment中的lifecycle（即this）
        shareViewModel = activity?.let {
            ViewModelProvider(it).get(ShareViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        btn_send.setOnClickListener {
            shareViewModel.sendData("数据包")
        }
        shareViewModel.getShareLiveData().observe(this, Observer<String> {
            tv_data.text = "firstFragment\n接收成功"
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

}