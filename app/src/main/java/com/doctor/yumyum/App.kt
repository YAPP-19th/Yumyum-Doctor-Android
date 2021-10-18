package com.doctor.yumyum

import android.app.Application
import android.content.SharedPreferences

class App : Application() {
    lateinit var sharedPref: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.shared_pref_key),
            MODE_PRIVATE
        )
    }
}