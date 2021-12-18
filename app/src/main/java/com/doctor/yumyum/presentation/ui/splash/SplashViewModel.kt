package com.doctor.yumyum.presentation.ui.splash

import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.SignInModel
import com.doctor.yumyum.data.repository.LoginRepositoryImpl
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

class SplashViewModel : BaseViewModel() {
    private val repository: LoginRepositoryImpl = LoginRepositoryImpl()
    val loginToken: String? = repository.getLoginToken()

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
                    }
                }
            }
        } catch (e: Exception) {
        }
    }
}