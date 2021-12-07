package com.doctor.yumyum.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivitySplashBinding
import com.doctor.yumyum.presentation.ui.login.LoginActivity
import com.doctor.yumyum.presentation.ui.main.MainActivity
import com.doctor.yumyum.presentation.ui.taste.TasteActivity
import java.util.*

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
        startActivity(Intent(this, TasteActivity::class.java))
//        if (viewModel.loginToken.isNullOrBlank()) {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
//        else {
//            startActivity(Intent(this, MainActivity::class.java))
//        }
    }
}