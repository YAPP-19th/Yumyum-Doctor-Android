package com.doctor.yumyum.presentation.ui.login

import android.os.Bundle
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityLoginBinding

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.presentation.viewmodel.LoginViewModel
import com.kakao.sdk.auth.model.OAuthToken

import com.kakao.sdk.auth.LoginClient


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    val TAG = "로그"
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewmodel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[LoginViewModel::class.java]

        //binding
        binding.apply {
            lifecycleOwner = this@LoginActivity
            viewModel = viewModel
            loginBtnKakao.setOnClickListener {
                // 로그인 callback
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error)
                    }
                    else if (token != null) {
                        Log.i(TAG, "로그인 성공 ${token.accessToken}")
                    }
                }

                LoginClient.instance.run {
                    // 카카오톡 있을 때
                    if (this.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                        this.loginWithKakaoTalk(this@LoginActivity, callback = callback)
                    }
                    // 카카오톡 없을 때
                    else {
                        this.loginWithKakaoAccount(this@LoginActivity, callback = callback)
                    }
                }

            }
        }
    }

}