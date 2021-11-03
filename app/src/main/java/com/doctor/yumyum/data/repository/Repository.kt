package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl

interface Repository {
    val localDataSource: LocalDataSourceImpl

    // 로그인 토큰
    fun getLoginToken(): String?
    fun setLoginToken(loginToken: String)
}

class RepositoryImpl : Repository {
    override val localDataSource: LocalDataSourceImpl
        get() = LocalDataSourceImpl()

    override fun getLoginToken(): String? {
        return localDataSource.getLoginToken()
    }

    override fun setLoginToken(loginToken: String) {
        localDataSource.setLoginToken(loginToken)
    }
}