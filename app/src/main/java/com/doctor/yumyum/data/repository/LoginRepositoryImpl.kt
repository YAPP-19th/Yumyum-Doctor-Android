package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.model.SignUpModel
import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import com.doctor.yumyum.domain.repository.LoginRepository
import okhttp3.ResponseBody
import retrofit2.Response



class LoginRepositoryImpl : LoginRepository {
    private val localDataSource: LocalDataSourceImpl
        get() = LocalDataSourceImpl()
    private val remoteDataSource: RemoteDataSourceImpl
        get() = RemoteDataSourceImpl()

    override fun getLoginToken(): String? {
        return localDataSource.getLoginToken()
    }

    override fun setLoginToken(loginToken: String) {
        localDataSource.setLoginToken(loginToken)
    }

    override suspend fun signUp(signUpModel: SignUpModel): Response<ResponseBody> {
        return remoteDataSource.signUp(signUpModel)
    }
}