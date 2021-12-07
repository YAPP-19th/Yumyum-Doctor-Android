package com.doctor.yumyum.presentation.ui.write.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class WriteTagViewModel : BaseViewModel(){
    private val _tagListLiveData : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val tagListLiveData : LiveData<ArrayList<String>>
        get() = _tagListLiveData

    val tagInput : MutableLiveData<String> = MutableLiveData()

    init {
        _tagListLiveData.value = arrayListOf()
    }

    fun validTagItem(){
        if (!tagInput.value.isNullOrBlank()) {
            addTagItem(tagInput.value.toString())
        } else {
            toast("재료를 입력해주세요")
        }
    }

    private fun addTagItem(newTag : String){
        if(!_tagListLiveData.value?.contains(newTag)!! && _tagListLiveData.value?.size != 10){
            _tagListLiveData.value?.add(newTag)
            _tagListLiveData.value = _tagListLiveData.value
        }
    }
}