package com.doctor.yumyum.presentation.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.signUpModel
import com.doctor.yumyum.data.repository.LoginRepositoryImpl
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


class LoginViewModel : BaseViewModel() {
    val repository = LoginRepositoryImpl()
    private val _accessToken: MutableLiveData<String> = MutableLiveData()
    val accessToken: LiveData<String>
        get() = _accessToken
    private val _nickname: MutableLiveData<String> = MutableLiveData()
    val nickname: LiveData<String>
        get() = _nickname
    private val _oauthType: MutableLiveData<String> = MutableLiveData()
    val oauthType: LiveData<String>
        get() = _oauthType
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorState: LiveData<Boolean>
        get() = _errorState

    fun setAccessToken(t: String) {
        _accessToken.postValue(t)
    }

    fun setNickname(n: String) {
        _nickname.postValue(n)
    }

    fun setOauthType(l: String) {
        _oauthType.postValue(l)
    }

    fun signUp() {
        repository.postAuthCreation(
            signUpModel(
                accessToken.value.toString(),
                nickname.value.toString(),
                oauthType.value.toString()
            ),
        ).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    for (h in response.headers().toList()) {
                        if (h.first == "Authorization") {
                            repository.setLoginToken(h.second)
                        }
                    }
                } else {
                    _errorState.value = true
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _errorState.value = true
            }
        })

    }
}