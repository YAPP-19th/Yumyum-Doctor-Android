package com.doctor.yumyum.presentation.ui.myrecipe.viewmodel

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.common.utils.StatusType
import com.doctor.yumyum.data.repository.MainRepositoryImpl

class MyRecipeFilterViewModel : BaseViewModel() {
    private val mainRepository = MainRepositoryImpl()

    val mode : MutableLiveData<Int> = MutableLiveData(mainRepository.getMode())

    private val _status :MutableLiveData<StatusType> = MutableLiveData()
    val status : LiveData<StatusType> get() = _status

    val minPrice : MutableLiveData<String> = MutableLiveData()
    val maxPrice : MutableLiveData<String> = MutableLiveData()

    private val _category : MutableLiveData<String> = MutableLiveData()
    val category : LiveData<String> get() = _category

    private val _tasteList : MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())
    val tasteList : LiveData<ArrayList<String>> get() =_tasteList

    fun setStatus( type : StatusType){
        _status.value = type
        minPrice.value.isNullOrEmpty()
    }

    fun setCategory(view: View) {
        view as TextView
        val tempText = view.text.toString()
        _category.value = tempText
    }

    fun setTasteList(taste: String) {
        if (_tasteList.value?.contains(taste) == true) {
            _tasteList.value?.remove(taste)
        } else {
            _tasteList.value?.add(taste)
        }
        _tasteList.value = _tasteList.value
    }

    fun refresh(){
        _status.value = null
        minPrice.value = ""
        maxPrice.value = ""
        _category.value=""
        _tasteList.value?.clear()
        _tasteList.value = _tasteList.value
    }
}