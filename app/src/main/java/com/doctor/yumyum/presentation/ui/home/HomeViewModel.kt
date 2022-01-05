package com.doctor.yumyum.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.common.utils.gradeEnToKo
import com.doctor.yumyum.data.model.FavoriteRecipe
import com.doctor.yumyum.data.remote.response.FavoriteRecipeResponse
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
    private val _nickname: MutableLiveData<String> = MutableLiveData()
    val nickname: LiveData<String>
        get() = _nickname
    private val _grade: MutableLiveData<String> = MutableLiveData()
    val grade: LiveData<String>
        get() = _grade
    val userInfo: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(nickname) { value = isInfoReady() }
        addSource(grade) { value = isInfoReady() }
    }
    private val _favoriteList: MutableLiveData<ArrayList<FavoriteRecipe>> = MutableLiveData()
    val favoriteList: LiveData<ArrayList<FavoriteRecipe>>
        get() = _favoriteList
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

    suspend fun getUserInfo() {
        try {
            val userInfoResponse: Response<UserInfoResponse> = userRepository.getUserInfo()
            if (userInfoResponse.isSuccessful) {
                _nickname.postValue(userInfoResponse.body()?.userInfo?.nickname)
                _grade.postValue(userInfoResponse.body()?.userInfo?.grade?.let { gradeEnToKo(it) })
            } else {
                _errorState.postValue(true)
            }
        } catch (e: Exception) {
            _errorState.postValue(true)
        }
    }

    private fun isInfoReady(): Boolean =
        !nickname.value.isNullOrEmpty() && !grade.value.isNullOrEmpty()

    suspend fun getFavorite(categoryName: String) {
        try {
            val recipeFavoriteRecipeResponse: Response<FavoriteRecipeResponse> =
                recipeRepository.getFavorite(categoryName)

            if (recipeFavoriteRecipeResponse.isSuccessful) {
                _favoriteList.postValue(recipeFavoriteRecipeResponse.body()?.favoriteFoods)
            } else {
                _errorState.postValue(true)
            }
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