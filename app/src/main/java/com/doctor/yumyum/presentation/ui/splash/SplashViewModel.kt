package com.doctor.yumyum.presentation.ui.splash

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
    private val _state: MutableLiveData<Int> = MutableLiveData(0)
    val state: LiveData<Int>
        get() = _state

    suspend fun signIn(): Boolean {
        val accessToken = TokenManager.instance.getToken()?.accessToken
        val oauthType = repository.getLoginMode()

        if (accessToken.isNullOrEmpty()) {
            _state.postValue(LOGIN)
            return false
        }
        if (oauthType.isNullOrEmpty()) {
            _state.postValue(LOGIN)
            return false
        }

        try {
            val response: Response<ResponseBody> = repository.signIn(
                SignInModel(
                    oauthType, accessToken
                )
            )
            if (response.isSuccessful) {
                for (h in response.headers().toList()) {
                    if (h.first == "Authorization") {
                        repository.setLoginToken(h.second)
                        _state.postValue(MAIN)
                    }
                }
                return true
            }
            _state.postValue(LOGIN)
            return false
        } catch (e: Exception) {
            _state.postValue(LOGIN)
            return false
        }
    }

    companion object {
        const val LOGIN = 1
        const val MAIN = 2
    }
}