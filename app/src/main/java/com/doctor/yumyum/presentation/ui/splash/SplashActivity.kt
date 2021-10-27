package com.doctor.yumyum.presentation.ui.splash

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivitySplashBinding
import com.doctor.yumyum.presentation.viewmodel.SplashViewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewmodel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SplashViewModel::class.java]

        //binding
        binding.apply {
            lifecycleOwner = this@SplashActivity
            viewModel = viewModel
        }

        //네트워크 연결 체크
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected = cm.activeNetwork != null

        if (!isConnected) {
            InternetDialog().show(supportFragmentManager, "InternetDialog")
        }
        
    }
}