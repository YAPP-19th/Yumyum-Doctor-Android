package com.doctor.yumyum.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivitySplashBinding
import com.doctor.yumyum.presentation.ui.login.LoginActivity
import com.doctor.yumyum.presentation.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SplashViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        binding.apply {
            lifecycleOwner = this@SplashActivity
            viewModel = viewModel
        }

        viewModel.errorState.observe(this) { error ->
            if (error) startActivity(Intent(this, LoginActivity::class.java))
        }

        if (viewModel.loginToken.isNullOrBlank()) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        else {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.signIn()
            }
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}