package com.doctor.yumyum.presentation.ui.myrecipe.viewmodel

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.common.utils.SortType
import com.doctor.yumyum.data.model.FavoriteRecipe
import com.doctor.yumyum.common.utils.RecipeType
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.data.repository.MainRepositoryImpl
import com.doctor.yumyum.data.repository.MyRecipeRepositoryImpl
import com.doctor.yumyum.data.repository.RecipeRepositoryImpl
import com.doctor.yumyum.presentation.ui.recipedetail.RecipeDetailViewModel
import kotlinx.coroutines.*


class MyRecipeViewModel : BaseViewModel() {
    private val repository = MainRepositoryImpl()
    private val myRecipeRepository = MyRecipeRepositoryImpl()
    private val recipeRepository = RecipeRepositoryImpl()

    private val _mode: MutableLiveData<Int> = MutableLiveData(repository.getMode())
    val mode: LiveData<Int>
        get() = _mode

    private val _errorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> get() = _errorState

    private var _sortType: MutableLiveData<SortType> = MutableLiveData(SortType.RECENT)
    val sortType: LiveData<SortType> get() = _sortType
    private var _tmpSortType: MutableLiveData<SortType> = MutableLiveData()
    val tmpSortType: LiveData<SortType> get() = _tmpSortType

    private val _favoriteRecipeList: MutableLiveData<ArrayList<FavoriteRecipe>> = MutableLiveData()
    val favoriteRecipeList: LiveData<ArrayList<FavoriteRecipe>>
        get() = _favoriteRecipeList

    private val _recipeType: MutableLiveData<RecipeType> = MutableLiveData()
    val recipeType: LiveData<RecipeType>
        get() = _recipeType

    private val _categoryName: MutableLiveData<String> = MutableLiveData()
    val categoryName: LiveData<String>
        get() = _categoryName

    private val _myRecipeList: MutableLiveData<ArrayList<RecipeModel>> = MutableLiveData()
    val myRecipeList: LiveData<ArrayList<RecipeModel>>
        get() = _myRecipeList

    fun initSortType() {
        _tmpSortType.value = sortType.value
    }

    fun setTmpSortType(type: SortType) {
        _tmpSortType.value = type
    }

    fun setSortType() {
        _sortType.value = tmpSortType.value
    }

    fun setCategoryName(categoryName: String) {
        _categoryName.value = categoryName
    }

    fun changeMode() {
        _mode.value =
            if (mode.value == R.string.common_food) R.string.common_beverage else R.string.common_food
        repository.setMode(mode.value ?: R.string.common_food)
    }

    fun changeRecipeType(type: RecipeType) {
        _recipeType.value = type
    }

    fun getMyRecipe(
        categoryName: String,
        mineFoodType: String,
        flavor: ArrayList<String>,
        minPrice: String?,
        maxPrice: String?,
        status: String?,
        sort: String,
        order: String,
    ) {
        viewModelScope.launch {
            val response = viewModelScope.async(Dispatchers.IO) {
                myRecipeRepository.getMyRecipe(
                    categoryName = categoryName,
                    flavors = flavor,
                    tags = "",
                    offset = 0,
                    pageSize = 10,
                    mineFoodType = mineFoodType,
                    minPrice = if (minPrice?.isNotBlank() == true) minPrice.toInt() else 0,
                    maxPrice = if (maxPrice?.isNotBlank() == true) maxPrice.toInt() else Int.MAX_VALUE,
                    firstSearchTime = "2022-12-20T12:12:12",
                    sort = sort,
                    order = order,
                    status = status
                )
            }

            if (response.await().isSuccessful) {
                response.await().body()?.foods?.let {
                    _myRecipeList.postValue(it)
                }
            } else {
                Log.d("getMyRecipe:", response.await().errorBody().toString())
            }
        }
    }


    fun getFavoriteRecipe(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    recipeRepository.getFavorite(categoryName)
                if (response.isSuccessful) {
                    response.body()?.favoriteFoods?.let {
                        _favoriteRecipeList.postValue(it)
                    }
                }
            } catch (e: Exception) {
                _errorState.postValue(R.string.error_favorite_count_limit)
            }
        }
    }

    suspend fun deleteFavorite(recipeId: Int) {
        val response = myRecipeRepository.deleteFavorite(recipeId)
        if (!response.isSuccessful) {
            Log.d("MyRecipeViewModel: ", "FavoriteDelete failed - ${response.code()}")
        }
    }

    suspend fun postFavorite(recipeId: Int, categoryName: String) {
        val response = myRecipeRepository.postFavorite(recipeId, categoryName)
        getFavoriteRecipe(categoryName)
        if (!response.isSuccessful) {
            Log.d("MyRecipeViewModel: ", "FavoritePost failed - ${response.code()}")
        }
    }

    suspend fun deleteBookMark(recipeId: Int) {
        val response = recipeRepository.deleteBookmark(recipeId)
        if (!response.isSuccessful) {
            Log.d("MyRecipeViewModel: ", "BookMarkDelete failed - ${response.code()}")
        }
    }
}


