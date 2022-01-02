package com.doctor.yumyum.presentation.ui.myrecipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.FavoriteRecipe
import com.doctor.yumyum.common.utils.MineFoodType
import com.doctor.yumyum.data.model.BestRecipe
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.data.model.WriteRecipe
import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import com.doctor.yumyum.data.repository.MainRepositoryImpl
import com.doctor.yumyum.data.repository.MyRecipeRepositoryImpl
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class MyRecipeViewModel : BaseViewModel() {
    private val repository = MainRepositoryImpl()
    private val myRecipeRepository = MyRecipeRepositoryImpl()

    private val _mode: MutableLiveData<Int> = MutableLiveData(repository.getMode())
    val mode: LiveData<Int>
        get() = _mode

    private val _bestRecipeList : MutableLiveData<ArrayList<FavoriteRecipe>> = MutableLiveData()
    val bestRecipeList : LiveData<ArrayList<FavoriteRecipe>>
    private val _foodType: MutableLiveData<String> = MutableLiveData(MineFoodType.MYFOOD.name)
    val foodType: LiveData<String>
        get() = _foodType

    private val _bestRecipeList: MutableLiveData<ArrayList<BestRecipe>> = MutableLiveData()
    val bestRecipeList: LiveData<ArrayList<BestRecipe>>
        get() = _bestRecipeList

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
}


