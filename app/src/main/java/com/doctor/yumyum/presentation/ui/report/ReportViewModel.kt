package com.doctor.yumyum.presentation.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.launch

class ReportViewModel : BaseViewModel() {
    private val _reportType: MutableLiveData<Int> = MutableLiveData(REASON_DUPLICATE)
    val reportType: LiveData<Int> get() = _reportType
    private val _reportMessage: MutableLiveData<String> = MutableLiveData()
    val reportMessage: LiveData<String> get() = _reportMessage
    private val _recipeId: MutableLiveData<Int> = MutableLiveData()
    val recipeId: LiveData<Int> get() = _recipeId
    private val repository = RecipeRepositoryImpl()
    private val _errorState = MutableLiveData<Int>()
    val errorState: LiveData<Int> get() = _errorState
    private val _reportResult = MutableLiveData<Boolean>()
    val reportResult: LiveData<Boolean> get() = _reportResult

    fun setRecipeId(recipeId: Int) {
        _recipeId.value = recipeId
    }

    fun setReportType(type: Int) {
        _reportType.value = type
    }

    fun setReportMessage(message: String) {
        _reportMessage.value = message
    }

    fun reportRecipe() {
        val body = HashMap<String, Any>()
        body["foodReportMessage"] = reportMessage.value ?: ""
        viewModelScope.launch {
            try {
                val response = repository.reportRecipe(recipeId.value ?: 0, body)

                if (response.isSuccessful) {
                    _reportResult.postValue(true)
                } else {
                    _errorState.postValue(ERROR_REPORT_FAIL)
                }
            } catch (e: Exception) {
                _errorState.postValue(ERROR_REPORT_FAIL)
            }
        }
    }

    companion object {
        const val REASON_DUPLICATE = R.string.report_reason_duplicate
        const val REASON_ADVERTISING = R.string.report_reason_advertising
        const val REASON_IRRELEVANT = R.string.report_reason_irrelevant
        const val REASON_HARMFUL = R.string.report_reason_harmful
        const val REASON_WRONG = R.string.report_reason_wrong
        const val REASON_PORNO = R.string.report_reason_porno
        const val REASON_ETC = R.string.common_etc
        const val ERROR_REPORT_FAIL = R.string.error_report_fail
    }
}