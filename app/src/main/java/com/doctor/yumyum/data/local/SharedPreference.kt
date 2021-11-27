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
    fun getLoginToken(): String? {
        return pref?.getString(LOGIN_TOKEN, "")
    }
    fun setLoginToken(loginToken: String) {
        pref?.edit()?.putString(LOGIN_TOKEN, loginToken)?.apply()
    }

    // 모드
    private const val MODE = "MODE"
    fun getMode(): Int? = pref?.getInt(MODE, R.string.common_food)
    fun setMode(mode: Int) = pref?.edit()?.putInt(MODE, mode)?.apply()
}