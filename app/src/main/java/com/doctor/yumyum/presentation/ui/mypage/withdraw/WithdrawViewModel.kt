package com.doctor.yumyum.presentation.ui.mypage.withdraw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.repository.LoginRepositoryImpl
import com.doctor.yumyum.data.repository.UserRepositoryImpl
import com.kakao.sdk.user.UserApiClient

class WithdrawViewModel : BaseViewModel() {
    private val userRepository = UserRepositoryImpl()
    private val loginRepository = LoginRepositoryImpl()
    private val _withdrawType: MutableLiveData<Int> = MutableLiveData(REASON_RARELY)
    val withdrawType: LiveData<Int> get() = _withdrawType
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData()
    val errorState: LiveData<Boolean> get() = _errorState

    fun setWithdrawType(type: Int) {
        _withdrawType.value = type
    }

    suspend fun withdraw() {

        UserApiClient.instance.unlink { error ->
            if (error != null) {
                _errorState.value = true
            }
        }
        try {
            val response = userRepository.deleteUser()
            if (!response.isSuccessful) {
            }
        } catch (e: Exception) {
            _errorState.postValue(true)
        } finally {
            loginRepository.setLoginToken("")
            loginRepository.setLoginMode("")
            userRepository.setLocalGrade("")
        }
    }

    companion object {
        const val REASON_RARELY = 1
        const val REASON_UNCOMFORTABLE = 2
        const val REASON_UNPLEASANT = 3
        const val REASON_WRONG = 4
        const val REASON_ETC = 5
    }
}