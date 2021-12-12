package com.doctor.yumyum.presentation.ui.write.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import retrofit2.http.DELETE

class WriteTagViewModel : BaseViewModel(){
    private val _tagListLiveData : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val tagListLiveData : LiveData<ArrayList<String>>
        get() = _tagListLiveData

    private val _deleteStatus : MutableLiveData<Int> = MutableLiveData(DELETE_STATUS)
    val deleteStatus : LiveData<Int>
        get() = _deleteStatus

    val tagInput : MutableLiveData<String> = MutableLiveData()

    init {
        _tagListLiveData.value = arrayListOf()
    }

    companion object {
        const val DELETE_STATUS = 1000
        const val SELECT_DELETE_STATUS = 1001
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

    fun removeTagItem(removeTag : String){
        _tagListLiveData.value?.remove(removeTag)
        _tagListLiveData.value = _tagListLiveData.value
    }

    fun updateDeleteStatus(){
        if(_deleteStatus.value == DELETE_STATUS){
            _deleteStatus.value=SELECT_DELETE_STATUS
        }else{
            _deleteStatus.value= DELETE_STATUS
        }
    }
}