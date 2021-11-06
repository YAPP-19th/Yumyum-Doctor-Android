package com.doctor.yumyum.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivitySplashBinding
import com.doctor.yumyum.presentation.ui.login.LoginActivity
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
        //임시용 main activity로 넘어가게
        val mainIntent = Intent(this, LoginActivity::class.java)
        startActivity(mainIntent)

    }
}