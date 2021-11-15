package com.doctor.yumyum.presentation.ui.write.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class WriteTagViewModel : BaseViewModel(){
    val etTagItem : MutableLiveData<String> = MutableLiveData()

    private val _rvTagItem : MutableLiveData<String> = MutableLiveData()
    val rvTagItem : LiveData<String>
        get() = _rvTagItem

    fun setTagItem(){
        val recentTagItem = this.etTagItem.value.also {
            if (it.isNullOrBlank()) {
                toast("재료를 입력해주세요")
                return
            }
        }
        _rvTagItem.value = recentTagItem
    }
}