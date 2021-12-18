package com.doctor.yumyum.presentation.ui.taste

import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class TasteViewModel : BaseViewModel() {
    private val _tasteClassState: MutableLiveData<MutableList<String>> =
        MutableLiveData(mutableListOf())
    var tasteClassState: LiveData<MutableList<String>> = _tasteClassState

    private val _tasteDetailState: MutableLiveData<MutableList<String>> =
        MutableLiveData(mutableListOf())
    var tasteDetailState: LiveData<MutableList<String>> = _tasteDetailState

    private val _mode: MutableLiveData<Int> = MutableLiveData(0)
    val mode: LiveData<Int> = _mode

    fun tasteClassChange(taste: String) {
        if (_tasteClassState.value?.contains(taste) == true) {
            _tasteClassState.value!!.remove(taste)
        } else {
            _tasteClassState.value?.add(taste)
        }
        _tasteClassState.value = _tasteClassState.value
    }

    fun tasteDetailChange(view: View) {
        view as Button
        val taste: String = view.text as String
        if (_tasteDetailState.value?.contains(taste) == true) {
            _tasteDetailState.value!!.remove(taste)
        } else {
            _tasteDetailState.value?.add(taste)
        }
        _tasteDetailState.value = _tasteDetailState.value
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