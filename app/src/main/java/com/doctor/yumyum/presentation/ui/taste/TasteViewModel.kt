package com.doctor.yumyum.presentation.ui.taste

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class TasteViewModel : BaseViewModel() {
    private val _tasteClassState: MutableLiveData<MutableSet<String>> =
        MutableLiveData(mutableSetOf())
    var tasteClassState: LiveData<MutableSet<String>> = _tasteClassState

    fun tasteClassChange(taste: String) {
        if (_tasteClassState.value?.contains(taste) == true) {
            _tasteClassState.value!!.remove(taste)
        } else {
            _tasteClassState.value?.add(taste)
        }
        _tasteClassState.value = _tasteClassState.value
    }

    companion object {
        const val SWEET = "단맛"
        const val SALTY = "짠맛"
        const val SPICY = "매운맛"
        const val BITTER = "쓴맛"
        const val SOUR = "신맛"
    }
}