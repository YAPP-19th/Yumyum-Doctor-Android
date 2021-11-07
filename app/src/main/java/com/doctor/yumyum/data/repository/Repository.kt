package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.model.signUpModel
import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

interface Repository {
    val localDataSource: LocalDataSourceImpl
    val remoteDataSource: RemoteDataSourceImpl

    // 로그인 토큰
    fun getLoginToken(): String?
    fun setLoginToken(loginToken: String)

    // 회원가입
    fun postAuthCreation(signUpModel: signUpModel):retrofit2.Call<ResponseBody>
}

class RepositoryImpl : Repository {
    override val localDataSource: LocalDataSourceImpl
        get() = LocalDataSourceImpl()
    override val remoteDataSource: RemoteDataSourceImpl
        get() = RemoteDataSourceImpl()

    override fun getLoginToken(): String? {
        return localDataSource.getLoginToken()
    }

    override fun setLoginToken(loginToken: String) {
        localDataSource.setLoginToken(loginToken)
    }

    override fun postAuthCreation(signUpModel: signUpModel): Call<ResponseBody> {
        return remoteDataSource.postAuthCreation(signUpModel)
    }
}