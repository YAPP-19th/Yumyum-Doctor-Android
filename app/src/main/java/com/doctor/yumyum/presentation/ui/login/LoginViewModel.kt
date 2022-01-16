package com.doctor.yumyum.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.SignInModel
import com.doctor.yumyum.data.model.SignUpModel
import com.doctor.yumyum.data.repository.LoginRepositoryImpl
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception


class LoginViewModel : BaseViewModel() {
    private val repository = LoginRepositoryImpl()
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorState: LiveData<Boolean>
        get() = _errorState
    private val _isLogin: MutableLiveData<Boolean> = MutableLiveData()
    val isLogin: LiveData<Boolean>
        get() = _isLogin


    suspend fun signUp(accessToken: String, nickname: String, oauthType: String) {
        try {
            val response: Response<ResponseBody> = repository.signUp(
                SignUpModel(
                    accessToken,
                    nickname,
                    oauthType
                )
            )
            if (response.isSuccessful) {
                for (h in response.headers().toList()) {
                    if (h.first == "Authorization") {
                        repository.setLoginToken(h.second)
                        repository.setLoginMode(oauthType)
                        _isLogin.postValue(false)
                    }
                }
            } else if (response.code() == 409) {
                signIn(accessToken, oauthType)
            } else {
                _errorState.postValue(true)
            }
        } catch (e: Exception) {
            _errorState.postValue(true)
        }
    }

    suspend fun signIn(accessToken: String, oauthType: String) {
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
                        repository.setLoginMode(oauthType)
                        _isLogin.postValue(true)
                    }
                }
            } else {
                _errorState.postValue(true)
            }
        } catch (e: Exception) {
        }
    }
}