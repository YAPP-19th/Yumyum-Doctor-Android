package com.doctor.yumyum.presentation.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.repository.LoginRepositoryImpl

class SplashViewModel : BaseViewModel() {
    private val repository: LoginRepositoryImpl = LoginRepositoryImpl()
    val loginToken: String? = repository.getLoginToken()
    private val _isLogin: MutableLiveData<Boolean> = MutableLiveData()
    val isLogin: LiveData<Boolean>
        get() = _isLogin

    suspend fun signIn() {
        val accessToken = repository.getLoginToken()
        val oauthType = repository.getLoginMode()

        if (accessToken.isNullOrEmpty() || oauthType.isNullOrEmpty()) _isLogin.postValue(false)

        try {
            val response = repository.refreshToken()
            if (response.isSuccessful) {
                for (h in response.headers().toList()) {
                    if (h.first == "Authorization") {
                        repository.setLoginToken(h.second)
                        _isLogin.postValue(true)
                    }
                }
            } else {
                _isLogin.postValue(false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _isLogin.postValue(false)
        }
    }
}