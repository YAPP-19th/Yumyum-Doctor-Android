package com.doctor.yumyum.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.data.remote.response.RecipeRecommendationResponse
import com.doctor.yumyum.data.remote.response.UserInfoResponse
import com.doctor.yumyum.data.repository.MainRepositoryImpl
import com.doctor.yumyum.data.repository.RecipeRepositoryImpl
import com.doctor.yumyum.data.repository.UserRepositoryImpl
import retrofit2.Response
import java.lang.Exception

class HomeViewModel : BaseViewModel() {
    private val mainRepository = MainRepositoryImpl()
    private val userRepository = UserRepositoryImpl()
    private val recipeRepository = RecipeRepositoryImpl()
    private val _mode: MutableLiveData<Int> = MutableLiveData(mainRepository.getMode())
    val mode: LiveData<Int>
        get() = _mode
    private val _nickname: MutableLiveData<String> = MutableLiveData("")
    val nickname: LiveData<String>
        get() = _nickname
    private val _recommendationList: MutableLiveData<ArrayList<RecipeModel>> = MutableLiveData()
    val recommendationList: LiveData<ArrayList<RecipeModel>>
        get() = _recommendationList
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorState: LiveData<Boolean>
        get() = _errorState

    fun changeMode() {
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

    suspend fun getRecommendation(categoryName: String) {
        try {
            val recipeRecommendationResponse: Response<RecipeRecommendationResponse> =
                recipeRepository.getRecommendation(
                    categoryName = categoryName,
                    top = 7,
                    rankDatePeriod = 7
                )
            _recommendationList.postValue(recipeRecommendationResponse.body()?.recommendationFoods)
        } catch (e: Exception) {
            _errorState.postValue(true)
        }
    }
}