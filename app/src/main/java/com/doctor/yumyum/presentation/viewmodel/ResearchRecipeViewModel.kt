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
        MutableLiveData(sharedPreferences.getInt("mode", R.string.food))
    val mode: LiveData<Int>
        get() = _mode

    fun changeMode() {
        _mode.value = if (mode.value == R.string.food) R.string.beverage else R.string.food

        // 현재 모드 저장
        val editor = sharedPreferences.edit()
        editor.putInt("mode", mode.value ?: R.string.food)
        editor.apply()
    }
}