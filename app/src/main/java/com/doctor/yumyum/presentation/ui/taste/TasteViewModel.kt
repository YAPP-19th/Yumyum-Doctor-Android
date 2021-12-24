package com.doctor.yumyum.presentation.ui.taste

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.UserFlavorModel
import com.doctor.yumyum.data.repository.UserFlavorRepositoryImpl
import okhttp3.ResponseBody
import retrofit2.Response

class TasteViewModel : BaseViewModel() {
    private val userFlavorRepository = UserFlavorRepositoryImpl()

    private val _tasteClassState: MutableLiveData<MutableList<String>> =
        MutableLiveData(mutableListOf())
    var tasteClassState: LiveData<MutableList<String>> = _tasteClassState

    private val _tasteDetailState: MutableLiveData<MutableList<String>> =
        MutableLiveData(mutableListOf())
    var tasteDetailState: LiveData<MutableList<String>> = _tasteDetailState

    val MODE_DETAIL = 1
    val MODE_CLASS = 0
    private val _mode: MutableLiveData<Int> = MutableLiveData(MODE_CLASS)
    val mode: LiveData<Int> = _mode



    fun tasteClassChange(taste: String) {
        if (_tasteClassState.value?.contains(taste) == true) {
            _tasteClassState.value?.remove(taste)
        } else {
            _tasteClassState.value?.add(taste)
        }
        _tasteClassState.value = _tasteClassState.value
    }

    fun tasteDetailChange(view: View) {
        view as Button
        val taste: String = view.text as String
        if (_tasteDetailState.value?.contains(taste) == true) {
            _tasteDetailState.value?.remove(taste)
        } else {
            _tasteDetailState.value?.add(taste)
        }
        _tasteDetailState.value = _tasteDetailState.value
    }

    fun setMode(mode: Int) {
        _mode.value = mode
    }

    suspend fun postFlavor(): Boolean {
        return try {
            val flavorList: List<String>? = tasteClassState.value
                ?.let { list1 -> tasteDetailState.value?.let { list2 -> list1 + list2 } }
                ?.toList()

            if (flavorList != null) {
                val userFlavorResponse: Response<ResponseBody> = userFlavorRepository.putFlavor(
                    UserFlavorModel(flavorList)
                )
//                if (userFlavorResponse.code() == 403) {
//                    //TODO : 허용되지 않은 사용자
//                }
                userFlavorResponse.code() == 200
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        const val SWEET = "단맛"
        const val SALTY = "짠맛"
        const val SPICY = "매운맛"
        const val BITTER = "쓴맛"
        const val SOUR = "신맛"
    }
}