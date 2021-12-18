package com.doctor.yumyum.presentation.ui.recipedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class RecipeDetailViewModel: BaseViewModel() {
    private val _addTagList : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val addTagList : LiveData<ArrayList<String>>
        get() = _addTagList

    private val _minusTagList : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val minusTagList : LiveData<ArrayList<String>>
        get() = _minusTagList

}