package com.doctor.yumyum.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.RankRecipe
import com.doctor.yumyum.data.remote.response.RankRecipeResponse
import com.doctor.yumyum.data.repository.MainRepositoryImpl
import retrofit2.Response

class ResearchRecipeViewModel : BaseViewModel() {
    private val repository = MainRepositoryImpl()
    private val _rankRecipes: MutableLiveData<List<RankRecipe>> = MutableLiveData()
    val rankRecipes: LiveData<List<RankRecipe>> get() = _rankRecipes

    @SuppressLint("SupportAnnotationUsage")
    @StringRes
    private val _mode: MutableLiveData<Int> = MutableLiveData(repository.getMode())
    val mode: LiveData<Int>
        get() = _mode

    fun changeMode() {
        _mode.value =
            if (mode.value == R.string.common_food) R.string.common_beverage else R.string.common_food

        // 현재 모드 저장
        repository.setMode(mode.value ?: R.string.common_food)
    }

    suspend fun getRankRecipe(categoryName: String, top: Int, rankDatePeriod: Int) {
        val response: Response<RankRecipeResponse> =
            repository.getRecipeRank(categoryName, top, rankDatePeriod)

        if (response.isSuccessful) {
            _rankRecipes.postValue(response.body()?.topRankingFoods)
        }
    }
}