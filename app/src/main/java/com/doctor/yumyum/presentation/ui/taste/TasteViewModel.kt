package com.doctor.yumyum.presentation.ui.taste

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class TasteViewModel : BaseViewModel() {
    private val _tasteState: MutableLiveData<MutableList<String>> =
        MutableLiveData(mutableListOf())
    var tasteState: LiveData<MutableList<String>> = _tasteState

    private val _mode: MutableLiveData<Int> = MutableLiveData(0)
    val mode: LiveData<Int> = _mode

    fun tasteClassChange(taste: String) {
        if (_tasteState.value?.contains(taste) == true) {
            _tasteState.value!!.remove(taste)
        } else {
            _tasteState.value?.add(taste)
        }
        _tasteState.value = _tasteState.value
    }

    fun tasteDetailChange(view: View) {
        view as Button
        val taste: String = view.text as String
        if (_tasteState.value?.contains(taste) == true) {
            _tasteState.value!!.remove(taste)
        } else {
            _tasteState.value?.add(taste)
        }
        _tasteState.value = _tasteState.value
    }

    fun setMode(mode: Int) {
        _mode.value = mode
    }

    companion object {
        const val SWEET = "단맛"
        const val SALTY = "짠맛"
        const val SPICY = "매운맛"
        const val BITTER = "쓴맛"
        const val SOUR = "신맛"
    }
}