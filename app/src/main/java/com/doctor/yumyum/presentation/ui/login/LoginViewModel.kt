package com.doctor.yumyum.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
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
                    }
                }
            } else {
                _errorState.postValue(true)
            }
        } catch (e: Exception) {
            _errorState.postValue(true)
        }
    }
}