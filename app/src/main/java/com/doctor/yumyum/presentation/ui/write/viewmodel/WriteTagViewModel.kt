package com.doctor.yumyum.presentation.ui.write.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class WriteTagViewModel : BaseViewModel(){
    val tagInput : MutableLiveData<String> = MutableLiveData()

    private val _tagList : MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())
    val tagList : LiveData<ArrayList<String>>
        get() = _tagList

    private val _deleteTagList : MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())
    val deleteTagList : LiveData<ArrayList<String>>
        get() = _deleteTagList

    private val _deleteStatus : MutableLiveData<Int> = MutableLiveData(DELETE_STATUS)
    val deleteStatus : LiveData<Int>
        get() = _deleteStatus

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
        if(!_tagList.value?.contains(newTag)!! && _tagList.value?.size != 10){
            _tagList.value?.add(newTag)
            _tagList.value = _tagList.value
        }
    }

    fun updateDeleteStatus(){
        if(_deleteStatus.value == DELETE_STATUS){
            _deleteStatus.value=SELECT_DELETE_STATUS
        }else if(_deleteStatus.value == SELECT_DELETE_STATUS){
            _deleteStatus.value= DELETE_STATUS
            deleteDeleteTagList()
        }
    }

    fun updateDeleteTagList(removeTag : String){
        if(_deleteTagList.value?.contains(removeTag) == true){
            // 삭제할 태그 선택 해제
            _deleteTagList.value?.remove(removeTag)
            _deleteTagList.value = _deleteTagList.value
        }else{
            // 삭제할 태그 선택
            _deleteTagList.value?.add(removeTag)
            _deleteTagList.value = _deleteTagList.value
        }
    }

    private fun deleteDeleteTagList(){
        // 선택된 태그들 삭제
        _deleteTagList.value?.let { _tagList.value?.removeAll(it) }
        _tagList.value = _tagList.value

        // 삭제할 리스트 초기화
        _deleteTagList.value?.clear()
        _deleteStatus.value = _deleteStatus.value
    }

}