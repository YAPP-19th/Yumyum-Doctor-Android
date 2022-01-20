package com.doctor.yumyum.presentation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.common.utils.gradeEnToKo
import com.doctor.yumyum.data.remote.response.UserInfoResponse
import com.doctor.yumyum.data.repository.LoginRepositoryImpl
import com.doctor.yumyum.data.repository.UserRepositoryImpl
import com.kakao.sdk.user.UserApiClient
import retrofit2.Response

class MyPageViewModel : BaseViewModel() {
    private val userRepository = UserRepositoryImpl()
    private val loginRepository = LoginRepositoryImpl()
    private val localGrade = userRepository.getLocalGrade()
    private val _nickname: MutableLiveData<String> = MutableLiveData("")
    val nickname: LiveData<String>
        get() = _nickname
    private val _grade: MutableLiveData<String> = MutableLiveData("")
    val grade: LiveData<String>
        get() = _grade
    private val _point: MutableLiveData<Int> = MutableLiveData()
    val point: LiveData<Int>
        get() = _point
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorState: LiveData<Boolean>
        get() = _errorState
    private val _gradeUp: MutableLiveData<String> = MutableLiveData("")
    val gradeUp: LiveData<String>
        get() = _gradeUp

    suspend fun getUserInfo() {
        try {
            val userInfoResponse: Response<UserInfoResponse> = userRepository.getUserInfo()
            if (userInfoResponse.isSuccessful) {
                _nickname.postValue(userInfoResponse.body()?.userInfo?.nickname)
                _grade.postValue(userInfoResponse.body()?.userInfo?.grade?.let { gradeEnToKo(it) })
                _point.postValue(userInfoResponse.body()?.userInfo?.userGradePoint)
                userInfoResponse.body()?.userInfo?.grade?.let { gradeEnToKo(it) }?.let {
                    isGradeUp(
                        it
                    )
                }
            } else {
                _errorState.postValue(true)
            }
        } catch (e: Exception) {
            _errorState.postValue(true)
        }
    }

    private fun isGradeUp(newGrade: String) {
        if ((!localGrade.isNullOrBlank())&&(localGrade != newGrade)) _gradeUp.value = newGrade
        userRepository.setLocalGrade(newGrade)
    }

    fun logout() {
        userRepository.setLocalGrade("")
        loginRepository.setLoginToken("")
        loginRepository.setLoginMode("")
        UserApiClient.instance.logout {
        }
    }
}