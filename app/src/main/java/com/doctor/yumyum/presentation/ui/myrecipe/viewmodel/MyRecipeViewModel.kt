package com.doctor.yumyum.presentation.ui.myrecipe.viewmodel

import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

    private val _foodType: MutableLiveData<String> = MutableLiveData(RecipeType.MYFOOD.name)
    val foodType: LiveData<String>
        get() = _foodType

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

    fun changeMode() {
        _mode.value =
            if (mode.value == R.string.common_food) R.string.common_beverage else R.string.common_food
        repository.setMode(mode.value ?: R.string.common_food)
    }

    fun changeFoodType(foodType: String) {
        _foodType.value = foodType
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    myRecipeRepository.getMyRecipe(
                        categoryName = categoryName,
                        flavors = flavor,
                        tags = "",
                        offset = 0,
                        pageSize = 10,
                        mineFoodType = mineFoodType,
                        minPrice = minPrice?.toInt(),
                        maxPrice = maxPrice?.toInt(),
                        firstSearchTime = "2022-12-20T12:12:12",
                        sort = sort,
                        order = order,
                        status = status
                    )
                if (response.isSuccessful) {
                    response.body()?.foods?.let {
                        _myRecipeList.postValue(it)
                    }
                }
            } catch (e: Exception) {
                Log.d("MyRecipeViewModel: ", "MyRecipeGet 실패")
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
                _errorState.postValue(R.string.error_post_favorite_fail)
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

    suspend fun deleteRecipe(recipeId: Int) {
        try {
            recipeRepository.deleteRecipe(recipeId)
        } catch (e: java.lang.Exception) {
            _errorState.postValue(RecipeDetailViewModel.ERROR_DELETE_RECIPE)
        }

    }
}


