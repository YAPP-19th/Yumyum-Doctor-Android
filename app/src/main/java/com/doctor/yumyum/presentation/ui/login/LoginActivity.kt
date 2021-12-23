package com.doctor.yumyum.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityLoginBinding
import com.doctor.yumyum.presentation.ui.nickname.NicknameActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

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
                CoroutineScope(Dispatchers.IO).launch {
                    kakaoSignUp()
                }
            }
        }

        // 회원가입 에러 처리
        viewModel.errorState.observe(this) { error ->
            if (error == true) {
                ErrorDialog().apply {
                    show(supportFragmentManager, "ErrorDialog")
                }
            }
        }
    }

    suspend fun kakaoSignUp() {

        try {
            val accessToken = kakaoLogin()
            val nickname = kakaoUserInfo()
            viewModel.signUp(accessToken, nickname,"KAKAO")
            startActivity(Intent(this, NicknameActivity::class.java))

        } catch (e: Exception) {
            ErrorDialog().apply {
                show(supportFragmentManager, "ErrorDialog")
            }
        }
    }

    private suspend fun kakaoLogin() = suspendCancellableCoroutine<String> { cont ->
        // 로그인 callback
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            // 로그인 여부
            error?.let {
                cont.resumeWithException(it)
            }
            token?.let {
                cont.resume(it.accessToken)
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

    private suspend fun kakaoUserInfo() = suspendCancellableCoroutine<String> { cont ->
        // 사용자 정보 요청 여부
        UserApiClient.instance.me { user, error ->
            error?.let {
                cont.resumeWithException(it)
            }
            user?.kakaoAccount?.profile?.nickname?.let { nickname ->
                cont.resume(nickname)
            }
        }
    }
}