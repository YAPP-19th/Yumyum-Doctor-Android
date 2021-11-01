package com.doctor.yumyum.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.presentation.ui.main.MainActivity




class LoginViewModel : BaseViewModel() {

    private val _login: MutableLiveData<Int> = MutableLiveData()
    val login: LiveData<Int>
        get() = _login

    fun kakaoLogin() {
    }

    fun googleLogin() {
        //TODO
    }
}