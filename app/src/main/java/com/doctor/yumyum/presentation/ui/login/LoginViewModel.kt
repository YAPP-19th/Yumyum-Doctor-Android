package com.doctor.yumyum.presentation.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.signUpModel
import com.doctor.yumyum.data.repository.RepositoryImpl
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Header


class LoginViewModel : BaseViewModel() {
    val TAG = "로그:LoginViewModel"
    val repository: RepositoryImpl = RepositoryImpl()
    private val _accessToken: MutableLiveData<String> = MutableLiveData()
    val accessToken: LiveData<String>
        get() = _accessToken
    private val _nickname: MutableLiveData<String> = MutableLiveData()
    val nickname: LiveData<String>
        get() = _nickname
    private val _oauthType: MutableLiveData<String> = MutableLiveData()
    val oauthType: LiveData<String>
        get() = _oauthType

    fun setAccessToken(t: String) {
        _accessToken.value = t
    }

    fun setNickname(n: String) {
        _nickname.value = n
    }

    fun setOauthType(l: String) {
        _oauthType.value = l
    }

    fun signUp() {
        Log.d(TAG, _accessToken.value.toString())
        Log.d(TAG, _nickname.value.toString())
        Log.d(TAG, _oauthType.value.toString())

        repository.postAuthCreation(
            signUpModel(
                _accessToken.value.toString(),
                _nickname.value.toString(),
                _oauthType.value.toString()
            ),
        ).enqueue(object : retrofit2.Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val a : List<Pair<String, String>> = response.headers().toList()
                    Log.d(TAG, a.toString())
                    for (h in a){
                        Log.d(TAG, "${h.first},${h.second}")
                    }

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }
        })

    }
}