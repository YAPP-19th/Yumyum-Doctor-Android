package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.model.signUpModel
import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import okhttp3.ResponseBody
import retrofit2.Response

interface LoginRepository {
    val localDataSource: LocalDataSourceImpl
    val remoteDataSource: RemoteDataSourceImpl

    // 로그인 토큰
    fun getLoginToken(): String?
    fun setLoginToken(loginToken: String)

    // 회원가입
    suspend fun postAuthCreation(signUpModel: signUpModel): Response<ResponseBody>
}