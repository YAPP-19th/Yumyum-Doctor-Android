package com.doctor.yumyum.presentation.ui.myrecipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.BestRecipe
import com.doctor.yumyum.data.repository.MainRepositoryImpl

class MyRecipeViewModel : BaseViewModel() {
    private val repository = MainRepositoryImpl()

    private val _mode: MutableLiveData<Int> = MutableLiveData(repository.getMode())
    val mode: LiveData<Int>
        get() = _mode

    private val _bestRecipeList: MutableLiveData<ArrayList<BestRecipe>> = MutableLiveData()
    val bestRecipeList: LiveData<ArrayList<BestRecipe>>
        get() = _bestRecipeList

    private val _myRecipeList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val myRecipeList: LiveData<ArrayList<String>>
        get() = _myRecipeList

    private val _foodType: MutableLiveData<String> = MutableLiveData(MineFoodType.MYFOOD.name)
    val foodType: LiveData<String>
        get() = _foodType

    fun changeMode() {
        _mode.value =
            if (mode.value == R.string.common_food) R.string.common_beverage else R.string.common_food
        repository.setMode(mode.value ?: R.string.common_food)
    }

    fun changeFoodType(foodType : MineFoodType) {
        _foodType.value = foodType.name
    }

}

enum class MineFoodType {
    MYFOOD, BOOKMARK
}
