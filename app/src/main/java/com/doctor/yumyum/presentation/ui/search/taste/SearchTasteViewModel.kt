package com.doctor.yumyum.presentation.ui.search.taste

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class SearchTasteViewModel : BaseViewModel() {

    private val _tasteList = MutableLiveData<ArrayList<String>>(ArrayList())
    val tasteList: LiveData<ArrayList<String>> get() = _tasteList

    fun setTasteState(taste: String) {
        if (_tasteList.value?.contains(taste) == true) {
            _tasteList.value?.remove(taste)
        } else {
            _tasteList.value?.add(taste)
        }
        _tasteList.value = _tasteList.value
    }

    companion object {
        const val TASTE_SWEET = "단맛"
        const val TASTE_SALTY = "짠맛"
        const val TASTE_SOUR = "신맛"
        const val TASTE_SPICY = "매운맛"
        const val TASTE_BITTER = "쓴맛"

        const val TASTE_DETAIL_SWEET = "달콤한"
        const val TASTE_DETAIL_LIGHT = "담백한"
        const val TASTE_DETAIL_SOUR = "새콤한"
        const val TASTE_DETAIL_COOL = "시원한"
        const val TASTE_DETAIL_SALTY = "짭짤한"
        const val TASTE_DETAIL_GREASY = "느끼한"
        const val TASTE_DETAIL_ATTRACTIVE = "삼삼한"
        const val TASTE_DETAIL_SPICY = "매콤한"
        const val TASTE_DETAIL_HOT = "얼큰한"
        const val TASTE_DETAIL_NEAT = "깔끔한"
        const val TASTE_DETAIL_SAVORY = "고소한"
        const val TASTE_DETAIL_REFRESH = "개운한"
    }
}