package com.doctor.yumyum.presentation.ui.myrecipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.BestRecipe
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

    private val _foodType: MutableLiveData<String> = MutableLiveData(MineFoodType.MYFOOD.name)
    val foodType: LiveData<String>
        get() = _foodType

    private val _bestRecipeList: MutableLiveData<ArrayList<BestRecipe>> = MutableLiveData()
    val bestRecipeList: LiveData<ArrayList<BestRecipe>>
        get() = _bestRecipeList

    private val _myRecipeList: MutableLiveData<List<SearchRecipeResponse>> = MutableLiveData()
    val myRecipeList: LiveData<List<SearchRecipeResponse>>
        get() = _myRecipeList

    fun changeMode() {
        _mode.value =
            if (mode.value == R.string.common_food) R.string.common_beverage else R.string.common_food
        repository.setMode(mode.value ?: R.string.common_food)
    }

    fun changeFoodType(foodType : MineFoodType) {
        _foodType.value = foodType.name
    }


    suspend fun getMyRecipe(categoryName: String) {
        try {
            val myRecipeResponse: Response<SearchRecipeResponse> =
                myRecipeRepository.getMyRecipe(
                    categoryName = categoryName,
                    flavors = "",
                    tags = "",
                    offset = 0,
                    pageSize = 10,
                    mineFoodType = foodType.value.toString(),
                    minPrice = 0,
                    maxPrice = 10000,
                    firstSearchTime = "2022-12-20T12:12:12",
                    sort = "id",
                    order = "asc",
                    status = ""
                )

            if (myRecipeResponse.isSuccessful) {

            }
        } catch (e: Exception) {
            Log.d("MyRecipeViewModel: ","MyRecipeGet 실패")
        }
    }
}

enum class MineFoodType {
    MYFOOD, BOOKMARK
}
