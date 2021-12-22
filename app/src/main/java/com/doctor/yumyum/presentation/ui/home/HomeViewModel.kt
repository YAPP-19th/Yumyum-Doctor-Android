package com.doctor.yumyum.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.repository.MainRepositoryImpl

class HomeViewModel : BaseViewModel() {
    private val mainRepository = MainRepositoryImpl()

    private val _mode: MutableLiveData<Int> = MutableLiveData(mainRepository.getMode())
    val mode: LiveData<Int>
        get() = _mode

    fun changeMode() {
        _mode.value =
            if (mode.value == R.string.common_food) R.string.common_beverage else R.string.common_food

        mainRepository.setMode(mode.value ?: R.string.common_food)
    }
}