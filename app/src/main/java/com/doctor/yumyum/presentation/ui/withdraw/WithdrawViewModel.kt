package com.doctor.yumyum.presentation.ui.withdraw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class WithdrawViewModel : BaseViewModel() {
    private val _withdrawType: MutableLiveData<Int> = MutableLiveData(REASON_RARELY)
    val withdrawType: LiveData<Int> get() = _withdrawType

    fun setWithdrawType(type: Int) {
        _withdrawType.value = type
    }

    companion object {
        const val REASON_RARELY = 1
        const val REASON_UNCOMFORTABLE = 2
        const val REASON_UNPLEASANT = 3
        const val REASON_WRONG = 4
        const val REASON_ETC = 5
    }
}