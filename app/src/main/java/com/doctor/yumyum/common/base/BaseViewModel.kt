package com.doctor.yumyum.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    // toast message 띄울 때 사용
    private val _baseState = MutableLiveData<BaseState>()
    val baseState
        get() = _baseState

    sealed class BaseState {
        data class Toast(val string: String): BaseState()
    }

    private fun toast(message: String) {
        viewModelScope.launch {
            _baseState.value = BaseState.Toast(message)
        }
    }
}