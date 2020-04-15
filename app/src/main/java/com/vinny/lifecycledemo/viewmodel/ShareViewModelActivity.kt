package com.vinny.lifecycledemo.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vinny.lifecycledemo.R
import kotlinx.android.synthetic.main.activity_share_viewmodel.*

/**
 * Description : ShareViewModelActivity 同一个Activity下的Fragment之间共享数据
 * Created : CGG
 * Time : 2020/4/15
 * Version : 0.0.1
 */
class ShareViewModelActivity : AppCompatActivity() {

    private val fragments = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_viewmodel)
        initFragment()
        initTab()
    }

    private fun initFragment() {
        fragments.add(FirstFragment.getInstance())
        fragments.add(SecondFragment.getInstance())
    }

    private fun initTab() {
        rg_container.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_first -> switchPage(0)
                R.id.rb_second -> switchPage(1)
            }
        }
        switchPage(0)
    }

    /**
     * 页面切换
     */
    private fun switchPage(index: Int) {
        supportFragmentManager.beginTransaction().apply {
            for (i in fragments.indices) {
                if (i == index) {
                    continue
                }
                val f = fragments[i]
                if (f.isAdded) {
                    hide(f)
                }
            }
            val f = fragments[index]
            if (f.isAdded) {
                show(f)
            } else {
                add(R.id.fragment_container, f)
            }
        }.commitAllowingStateLoss()
    }

}