package com.doctor.yumyum.presentation.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        binding.apply {
            lifecycleOwner = this@LoginActivity
        }
    }
}