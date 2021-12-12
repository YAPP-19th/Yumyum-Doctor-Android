package com.doctor.yumyum.presentation.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel

class ReportViewModel: BaseViewModel() {
    private val _reportType: MutableLiveData<Int> = MutableLiveData(REASON_DUPLICATE)
    val reportType: LiveData<Int> get() = _reportType

    fun setReportType(type: Int) {
        _reportType.value = type
    }

    companion object {
        const val REASON_DUPLICATE = 1
        const val REASON_ADVERTISING = 2
        const val REASON_IRRELEVANT = 3
        const val REASON_HARMFUL = 4
        const val REASON_WRONG = 5
        const val REASON_ETC = 6
    }
}