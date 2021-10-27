package com.doctor.yumyum.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class ResearchRecipeViewModel(currentMode: String) : BaseViewModel() {
    private val _mode: MutableLiveData<String> = MutableLiveData(currentMode)
    val mode: LiveData<String>
        get() = _mode

    fun changeMode() {
        if (mode.value.equals("음식")) {
            _mode.value = "음료"
        } else {
            _mode.value = "음식"
        }
        // TODO: sharedpreferences에 현재 모드 저장
    }
}