package com.doctor.yumyum.presentation.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.doctor.yumyum.R
import com.doctor.yumyum.databinding.ActivityMainBinding
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.presentation.adapter.MainViewpagerAdapter
import com.doctor.yumyum.presentation.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewmodel
        vm = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        //binding
        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = vm
            mainVp.adapter = MainViewpagerAdapter(this@MainActivity)
            // 뷰페이저 변경하면 바텀네비게이션 변경
            mainVp.registerOnPageChangeCallback(
                object :ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        mainNvBottom.menu.getItem(position).isChecked = true
                    }
                }
            )
        }

    }
}