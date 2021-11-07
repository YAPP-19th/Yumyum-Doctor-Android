package com.doctor.yumyum.presentation.ui.login

import android.os.Bundle
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityLoginBinding

import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken

import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.user.UserApiClient


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
                kakaoLogin()
            }
            loginBtnGoogle.setOnClickListener {
                // TODO :: 준영
            }
        }
    }

    fun kakaoLogin() {
        viewModel.setOauthType("KAKAO")
        // 로그인 callback
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            // 로그인 여부
            if (error != null) {
                ErrorDialog().apply {
                    show(supportFragmentManager, "ErrorDialog")
                }
            } else if (token != null) {
                viewModel.setAccessToken(token.accessToken)
            }
            // 사용자 정보 요청 여부
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    ErrorDialog().apply {
                        show(supportFragmentManager, "ErrorDialog")
                    }
                } else if (user != null) {
                    user.kakaoAccount?.profile?.nickname?.let {
                        viewModel.setNickname(it)
                        viewModel.signUp()
                    }
                }
            }
        }
        LoginClient.instance.run {
            // 카카오톡 있을때
            if (this.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                this.loginWithKakaoTalk(this@LoginActivity, callback = callback)
            }
            // 카카오톡 없을때
            else {
                this.loginWithKakaoAccount(this@LoginActivity, callback = callback)
            }
        }
    }
}