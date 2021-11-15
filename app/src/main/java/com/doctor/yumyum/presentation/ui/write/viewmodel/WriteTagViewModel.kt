package com.doctor.yumyum.presentation.ui.write.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class WriteTagViewModel : BaseViewModel(){
    private val tagList : ArrayList<String> = arrayListOf()
    private val _rvTagList : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val rvTagList : LiveData<ArrayList<String>>
        get() = _rvTagList

    val etTagItem : MutableLiveData<String> = MutableLiveData()

    fun validTagItem(){
        if(!etTagItem.value.isNullOrBlank()){
            addTagItem(etTagItem.value!!)
        }else{toast("재료 입력해주세요")}
    }

    private fun addTagItem(newTag : String){
        if(!tagList.contains(newTag)){
            tagList.add(newTag)
            _rvTagList.value = tagList
        }
    }
}