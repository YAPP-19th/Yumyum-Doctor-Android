package com.doctor.yumyum.presentation.ui.splash

import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.repository.RepositoryImpl

class SplashViewModel : BaseViewModel() {
    private val repository: RepositoryImpl = RepositoryImpl()
    val loginToken:String? = repository.getLoginToken()
}