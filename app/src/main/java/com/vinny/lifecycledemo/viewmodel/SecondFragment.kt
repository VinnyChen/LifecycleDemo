package com.vinny.lifecycledemo.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vinny.lifecycledemo.R
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * Description : SecondFragment
 * Created : CGG
 * Time : 2020/4/15
 * Version : 0.0.1
 */
class SecondFragment : Fragment() {

    companion object {
        fun getInstance(): SecondFragment {
            return SecondFragment()
        }
    }

    private lateinit var shareViewModel: ShareViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //注意：此处ViewModelProvider必须填Activity中的lifecycle，不能传Fragment中的lifecycle（即this）
        shareViewModel = activity?.let {
            ViewModelProvider(it).get(ShareViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        shareViewModel.getShareLiveData().observe(this, Observer<String> {
            tv_data.text = "secondFragment\n接收成功"
        })
    }
}