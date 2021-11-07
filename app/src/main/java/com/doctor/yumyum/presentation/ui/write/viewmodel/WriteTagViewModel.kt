package com.doctor.yumyum.presentation.ui.write.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WriteTagViewModel {
    private val _guideText : MutableLiveData<String> = MutableLiveData()
    val guideText : LiveData<String>
        get() = _guideText

    private val _tagList : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val tagList : LiveData<ArrayList<String>>
        get() = _tagList

    fun setGuideText(type : String){
        _guideText.value = type
    }

    fun setTagList(newItem : String){
        _tagList.value = arrayListOf()
    }
}