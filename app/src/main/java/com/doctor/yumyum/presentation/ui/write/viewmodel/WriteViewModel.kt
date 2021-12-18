package com.doctor.yumyum.presentation.ui.write.viewmodel

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class WriteViewModel : BaseViewModel()  {
    private var _mode : MutableLiveData<Boolean> = MutableLiveData()
    val mode : LiveData<Boolean>
        get() = _mode

    private var _tempCategory : MutableLiveData<String> = MutableLiveData()
    val tempCategory : LiveData<String>
        get() = _tempCategory

    private var _category : MutableLiveData<String> = MutableLiveData()
    val category : LiveData<String>
        get() = _category

    var mainIngredient : MutableLiveData<String> = MutableLiveData()

    private val _addTagList : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val addTagList : LiveData<ArrayList<String>>
        get() = _addTagList

    private val _minusTagList : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val minusTagList : LiveData<ArrayList<String>>
        get() = _minusTagList

    fun setMode(isTurnOn: Boolean) {
        _mode.value = isTurnOn
    }

    fun setTempCategory(tempText : String ){
        _tempCategory.value = tempText
    }

    fun setCategory(){
        _category.value = _tempCategory.value
    }

    fun setAddTagItem(newTagList: ArrayList<String>?){
        _addTagList.value = newTagList
    }

    fun setMinusTagItem(newTagList: ArrayList<String>?){
        _minusTagList.value = newTagList
    }

}