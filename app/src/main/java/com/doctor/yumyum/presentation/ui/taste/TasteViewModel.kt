package com.doctor.yumyum.presentation.ui.taste

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class TasteViewModel : BaseViewModel() {
    private val _tasteClassState: MutableLiveData<MutableMap<String, Boolean>> =
        MutableLiveData(
            mutableMapOf(
                "SWEET" to false,
                "SALTY" to false,
                "SPICY" to false,
                "BITTER" to false,
                "SOUR" to false
            )
        )
    val tasteClassState: LiveData<MutableMap<String, Boolean>>
        get() = _tasteClassState

    fun tasteClassChange(taste: String) {
        val temp = _tasteClassState.value?.get(taste)
        if (temp != null) {
            _tasteClassState.value?.put(taste, !temp)
        }
    }
    companion object {
        const val SWEET = "SWEET"
        const val SALTY = "SALTY"
        const val SPICY = "SPICY"
        const val BITTER = "BITTER"
        const val SOUR = "SOUR"
    }
}