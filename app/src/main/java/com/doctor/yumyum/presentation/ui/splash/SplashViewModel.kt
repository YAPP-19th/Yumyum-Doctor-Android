package com.doctor.yumyum.presentation.ui.splash

import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.repository.LoginRepositoryImpl

class SplashViewModel : BaseViewModel() {
    private val repository: LoginRepositoryImpl = LoginRepositoryImpl()
    val loginToken:String? = repository.getLoginToken()
}