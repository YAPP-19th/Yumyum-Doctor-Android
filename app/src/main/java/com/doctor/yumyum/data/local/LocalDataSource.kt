package com.doctor.yumyum.data.local

interface LocalDataSource {

    fun getLoginToken(): String?
    fun setLoginToken(loginToken: String)

    fun getLoginMode(): String?
    fun setLoginMode(loginMode: String)

    fun getMode(): Int?
    fun setMode(mode: Int)
}

class LocalDataSourceImpl : LocalDataSource {
    override fun getLoginToken(): String? = SharedPreference.getLoginToken()

    override fun setLoginToken(loginToken: String) {
        SharedPreference.setLoginToken(loginToken)
    }

    override fun getLoginMode(): String? = SharedPreference.getLoginMode()

    override fun setLoginMode(loginMode: String) {
        SharedPreference.setLoginMode(loginMode)
    }

    override fun getMode(): Int? = SharedPreference.getMode()

    override fun setMode(mode: Int) {
        SharedPreference.setMode(mode)
    }
}