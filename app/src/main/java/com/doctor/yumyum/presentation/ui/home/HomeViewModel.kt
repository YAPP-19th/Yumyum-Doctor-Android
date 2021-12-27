package com.doctor.yumyum.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.remote.response.UserInfoResponse
import com.doctor.yumyum.data.repository.MainRepositoryImpl
import com.doctor.yumyum.data.repository.UserRepositoryImpl
import retrofit2.Response
import java.lang.Exception

class HomeViewModel : BaseViewModel() {
    private val mainRepository = MainRepositoryImpl()
    private val userRepository = UserRepositoryImpl()
    private val _mode: MutableLiveData<Int> = MutableLiveData(mainRepository.getMode())
    val mode: LiveData<Int>
        get() = _mode
    private val _nickname: MutableLiveData<String> = MutableLiveData("")
    val nickname: LiveData<String>
        get() = _nickname
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorState: LiveData<Boolean>
        get() = _errorState

    fun changeMode() {
        Log.d("로그", "sdf")
        _mode.value =
            if (mode.value == R.string.common_food) R.string.common_beverage else R.string.common_food
        mainRepository.setMode(mode.value ?: R.string.common_food)
    }

    suspend fun getUserNickname() {
        try {
            val userInfoResponse: Response<UserInfoResponse> = userRepository.getUserInfo()
            _nickname.postValue(userInfoResponse.body()?.userInfo?.nickname)
        } catch (e: Exception) {
            _errorState.postValue(true)
        }
    }
}