package com.doctor.yumyum.data.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.doctor.yumyum.R

object SharedPreference {
    private var pref: SharedPreferences? = null

    fun getInstance(context: Context): SharedPreference {
        if (pref == null) {
            pref = context.getSharedPreferences(
                context.getString(R.string.shared_pref_key),
                Application.MODE_PRIVATE
            )
        }
        return this
    }

    // 로그인 토큰
    private const val LOGIN_TOKEN = "LOGIN_TOKEN"
    fun getLoginToken(): String? = pref?.getString(LOGIN_TOKEN, "")
    fun setLoginToken(loginToken: String) =
        pref?.edit()?.putString(LOGIN_TOKEN, loginToken)?.apply()

    // 로그인 모드
    private const val LOGIN_MODE = "LOGIN_MODE"
    fun getLoginMode(): String? = pref?.getString(LOGIN_MODE, "KAKAO")
    fun setLoginMode(loginMode: String) = pref?.edit()?.putString(LOGIN_MODE, loginMode)?.apply()


    // 모드
    private const val MODE = "MODE"
    fun getMode(): Int? = pref?.getInt(MODE, R.string.common_food)
    fun setMode(mode: Int) = pref?.edit()?.putInt(MODE, mode)?.apply()
}