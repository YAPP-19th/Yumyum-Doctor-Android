package com.doctor.yumyum.data.local

interface LocalDataSource {

    // 로그인 토큰
    fun getLoginToken():String?
    fun setLoginToken(loginToken: String)
}

class LocalDataSourceImpl():LocalDataSource{
    override fun getLoginToken(): String? {
        return SharedPreference.getLoginToken()
    }
    override fun setLoginToken(loginToken: String) {
        SharedPreference.setLoginToken(loginToken)
    }
}