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

    private val _status :MutableLiveData<String> = MutableLiveData()
    val status : LiveData<String> get() = _status

    val minPrice : MutableLiveData<String> = MutableLiveData()
    val maxPrice : MutableLiveData<String> = MutableLiveData()

    private val _category : MutableLiveData<String> = MutableLiveData()
    val category : LiveData<String> get() = _category

    private val _tasteList : MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())
    val tasteList : LiveData<ArrayList<String>> get() =_tasteList

    fun setStatus(type : String){
        _status.value = type
        Log.d("filterLauncher: ViewModel",status.value.toString())
    }

    fun setCategory(view: View) {
        view as TextView
        val tempText = view.text.toString()
        _category.value = tempText
    }

    fun setCategory(category : String){
        _category.value = category
    }

    fun setTasteList(taste: String) {
        if (_tasteList.value?.contains(taste) == true) {
            _tasteList.value?.remove(taste)
        } else {
            _tasteList.value?.add(taste)
        }
        _tasteList.value = _tasteList.value
    }

    fun setTasteList(list : ArrayList<String>) {
        _tasteList.value = list
    }

}