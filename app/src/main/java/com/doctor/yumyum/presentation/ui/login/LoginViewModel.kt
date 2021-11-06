package com.doctor.yumyum.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.repository.RepositoryImpl
import com.doctor.yumyum.presentation.ui.main.MainActivity


class LoginViewModel : BaseViewModel() {
    val repository: RepositoryImpl = RepositoryImpl()

    fun kakaoLogin() {
    }

    fun googleLogin() {
    }
}