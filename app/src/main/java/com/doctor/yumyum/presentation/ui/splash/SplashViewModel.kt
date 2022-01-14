package com.doctor.yumyum.presentation.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.SignInModel
import com.doctor.yumyum.data.repository.LoginRepositoryImpl
import com.kakao.sdk.auth.TokenManager
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

class SplashViewModel : BaseViewModel() {
    private val repository: LoginRepositoryImpl = LoginRepositoryImpl()
    val loginToken: String? = repository.getLoginToken()
    private val _isLogin: MutableLiveData<Boolean> = MutableLiveData()
    val isLogin: LiveData<Boolean>
        get() = _isLogin

    suspend fun signIn() {
        val accessToken = loginToken
        val oauthType = repository.getLoginMode()
        _isLogin.postValue(!accessToken.isNullOrEmpty() && !oauthType.isNullOrEmpty())
    }
}