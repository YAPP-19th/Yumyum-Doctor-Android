package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.model.SignInModel
import com.doctor.yumyum.data.model.SignUpModel
import com.doctor.yumyum.data.remote.datasource.AuthDataSourceImpl
import com.doctor.yumyum.domain.repository.LoginRepository
import okhttp3.ResponseBody
import retrofit2.Response


class LoginRepositoryImpl : LoginRepository {
    private val localDataSource: LocalDataSourceImpl
        get() = LocalDataSourceImpl()
    private val authDataSource: AuthDataSourceImpl
        get() = AuthDataSourceImpl()

    override fun getLoginToken(): String? = localDataSource.getLoginToken()

    override fun setLoginToken(loginToken: String) {
        localDataSource.setLoginToken(loginToken)
    }

    override fun getLoginMode(): String? = localDataSource.getLoginMode()

    override fun setLoginMode(loginMode: String) {
        localDataSource.setLoginMode(loginMode)
    }

    override suspend fun signUp(signUpModel: SignUpModel): Response<ResponseBody> =
        authDataSource.signUp(signUpModel)

    override suspend fun signIn(signInModel: SignInModel): Response<ResponseBody> =
        authDataSource.signIn(signInModel)
}