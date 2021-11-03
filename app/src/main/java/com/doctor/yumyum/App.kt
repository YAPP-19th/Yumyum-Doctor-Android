package com.doctor.yumyum

import android.app.Application
import android.content.SharedPreferences
import com.doctor.yumyum.data.local.SharedPreference
import com.kakao.sdk.common.KakaoSdk

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // pref 초기화
        SharedPreference.getInstance(this)

        // KakaoSDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}