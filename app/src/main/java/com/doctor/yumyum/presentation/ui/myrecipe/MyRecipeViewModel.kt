package com.doctor.yumyum.presentation.ui.myrecipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.FavoriteRecipe
import com.doctor.yumyum.common.utils.MineFoodType
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.data.repository.MainRepositoryImpl
import com.doctor.yumyum.data.repository.MyRecipeRepositoryImpl
import com.doctor.yumyum.data.repository.RecipeRepositoryImpl


class MyRecipeViewModel : BaseViewModel() {
    private val repository = MainRepositoryImpl()
    private val myRecipeRepository = MyRecipeRepositoryImpl()
    private val recipeRepository = RecipeRepositoryImpl()

    private val _mode: MutableLiveData<Int> = MutableLiveData(repository.getMode())
    val mode: LiveData<Int>
        get() = _mode

    private val _favoriteRecipeList : MutableLiveData<ArrayList<FavoriteRecipe>> = MutableLiveData()
    val favoriteRecipeList : LiveData<ArrayList<FavoriteRecipe>>
        get() = _favoriteRecipeList

    private val _foodType: MutableLiveData<String> = MutableLiveData(MineFoodType.MYFOOD.name)
    val foodType: LiveData<String>
        get() = _foodType

    private val _myRecipeList: MutableLiveData<ArrayList<RecipeModel>> = MutableLiveData()
    val myRecipeList: LiveData<ArrayList<RecipeModel>>
        get() = _myRecipeList

    fun changeMode() {
        _mode.value =
            if (mode.value == R.string.common_food) R.string.common_beverage else R.string.common_food
        repository.setMode(mode.value ?: R.string.common_food)
    }

    fun changeFoodType(foodType : String) {
        _foodType.value = foodType
    }

    suspend fun getMyRecipe(categoryName: String, mineFoodType: String) {
        try {
            val response =
                myRecipeRepository.getMyRecipe(
                    categoryName = categoryName,
                    flavors = "",
                    tags = "",
                    offset = 0,
                    pageSize = 10,
                    mineFoodType = mineFoodType,
                    minPrice = null,
                    maxPrice = null,
                    firstSearchTime = "2022-12-20T12:12:12",
                    sort = "id",
                    order = "asc",
                    status = ""
                )
            response.body()?.foods?.let {
                _myRecipeList.postValue(it)
            }
        } catch (e: Exception) {
            Log.d("MyRecipeViewModel: ","MyRecipeGet 실패")
        }
    }

    suspend fun getFavoriteRecipe(categoryName: String){
        try {
            val response =
                recipeRepository.getFavorite(categoryName)
            response.body()?.favoriteFoods?.let {
                _favoriteRecipeList.postValue(it)
            }
        }catch (e: Exception){
            Log.d("MyRecipeViewModel: ","FavoriteGet failed - ${e.message}")
        }
    }
}


