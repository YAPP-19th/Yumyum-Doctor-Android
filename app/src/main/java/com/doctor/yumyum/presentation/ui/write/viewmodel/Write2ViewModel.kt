package com.doctor.yumyum.presentation.ui.write.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class Write2ViewModel : BaseViewModel(){
    private val _addListLiveData : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val addListLiveData : LiveData<ArrayList<String>>
        get() = _addListLiveData

    private val _minusListLiveData : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val minusListLiveData : LiveData<ArrayList<String>>
        get() = _minusListLiveData

    fun setAddTagItem(newTagList: ArrayList<String>?){
        _addListLiveData.value = newTagList
        Log.d("Write2ViewModel",_addListLiveData.value.toString())
    }

    fun setMinusTagItem(newTagList: ArrayList<String>?){
        _minusListLiveData.value = newTagList
    }
}