package com.doctor.yumyum.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel

class ResearchRecipeViewModel(private val sharedPreferences: SharedPreferences) : BaseViewModel() {
    @SuppressLint("SupportAnnotationUsage")
    @StringRes
    private val _mode: MutableLiveData<Int> =
        MutableLiveData(sharedPreferences.getInt("mode", R.string.common_food))
    val mode: LiveData<Int>
        get() = _mode

    fun changeMode() {
        _mode.value =
            if (mode.value == R.string.common_food) R.string.common_beverage else R.string.common_food

        // 현재 모드 저장
        val editor = sharedPreferences.edit()
        editor.putInt("mode", mode.value ?: R.string.common_food)
        editor.apply()
    }
}