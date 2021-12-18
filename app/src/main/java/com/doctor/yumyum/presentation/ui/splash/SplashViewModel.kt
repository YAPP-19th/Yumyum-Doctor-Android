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
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorState: LiveData<Boolean>
        get() = _errorState

    suspend fun signIn(): Boolean {
        val accessToken = TokenManager.instance.getToken()?.accessToken
        val oauthType = repository.getLoginMode()

        if (accessToken.isNullOrEmpty()) {
            _errorState.postValue(true)
            return false
        }
        if (oauthType.isNullOrEmpty()) {
            _errorState.postValue(true)
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
                    }
                }
                return true
            }
            _errorState.postValue(true)
            return false
        } catch (e: Exception) {
            _errorState.postValue(true)
            return false
        }
    }
}