package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.model.SignInModel
import com.doctor.yumyum.data.model.SignUpModel
import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import okhttp3.ResponseBody
import retrofit2.Response

interface LoginRepository {

    // 로그인 토큰
    fun getLoginToken(): String?
    fun setLoginToken(loginToken: String)

    fun getLoginMode(): String?
    fun setLoginMode(loginMode: String)

    // 회원가입
    suspend fun signUp(signUpModel: SignUpModel): Response<ResponseBody>

    // 로그인
    suspend fun signIn(signInModel: SignInModel): Response<ResponseBody>

    // 리프레스 토큰
    suspend fun refreshToken(): Response<ResponseBody>
}