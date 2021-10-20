package com.doctor.yumyum.presentation.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.databinding.ActivityMainBinding
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.presentation.viewmodel.MainViewModel

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
        }

    }
}