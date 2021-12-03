package com.doctor.yumyum.data.local

interface LocalDataSource {

    // 로그인 토큰
    fun getLoginToken():String?
    fun setLoginToken(loginToken: String)

    fun getMode(): Int?
    fun setMode(mode: Int)
}

class LocalDataSourceImpl :LocalDataSource{
    override fun getLoginToken(): String? {
        return SharedPreference.getLoginToken()
    }
    override fun setLoginToken(loginToken: String) {
        SharedPreference.setLoginToken(loginToken)
    }

    override fun getMode(): Int? = SharedPreference.getMode()

    override fun setMode(mode: Int) {
        SharedPreference.setMode(mode)
    }
}