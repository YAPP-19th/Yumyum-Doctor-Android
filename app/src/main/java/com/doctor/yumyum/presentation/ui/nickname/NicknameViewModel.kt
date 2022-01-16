package com.doctor.yumyum.presentation.ui.nickname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.remote.response.NicknameResponse
import com.doctor.yumyum.data.remote.response.UserInfoResponse
import com.doctor.yumyum.data.repository.UserRepositoryImpl
import okhttp3.ResponseBody
import retrofit2.Response

class NicknameViewModel : BaseViewModel() {
    private val userRepository = UserRepositoryImpl()
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorState: LiveData<Boolean>
        get() = _errorState
    val nickname: MutableLiveData<String> = MutableLiveData("")

    suspend fun getNickname() {
        try {
            val nicknameResponse: Response<NicknameResponse> = userRepository.getNickname()
            nickname.postValue(nicknameResponse.body()?.nickname)

        } catch (e: Exception) {
            _errorState.postValue(true)
        }
    }

    suspend fun getUserInfo() {
        try {
            val userInfoResponse: Response<UserInfoResponse> = userRepository.getUserInfo()
            if (userInfoResponse.isSuccessful) {
                nickname.postValue(userInfoResponse.body()?.userInfo?.nickname)
            } else {
                _errorState.postValue(true)
            }
        } catch (e: Exception) {
            _errorState.postValue(true)
        }
    }

    suspend fun validateNickname(nickname: String): Boolean {
        return try {
            val nicknameResponse: Response<ResponseBody> =
                userRepository.validateNickname(nickname)
            nicknameResponse.code() == 200

        } catch (e: Exception) {
            _errorState.postValue(true)
            false
        }
    }

    suspend fun patchNickname(nickname: String) {
        try {
            val nicknameResponse: Response<ResponseBody> =
                userRepository.patchNickname(nickname)
        } catch (e:Exception) {
            _errorState.postValue(true)
        }
    }
}