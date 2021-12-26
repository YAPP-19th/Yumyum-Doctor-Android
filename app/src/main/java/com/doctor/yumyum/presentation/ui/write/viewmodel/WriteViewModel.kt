package com.doctor.yumyum.presentation.ui.write.viewmodel

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.FoodFlavor
import okhttp3.MultipartBody

class WriteViewModel : BaseViewModel()  {
    private var _mode : MutableLiveData<Boolean> = MutableLiveData()
    val mode : LiveData<Boolean>
        get() = _mode

    private var _tempCategory : MutableLiveData<String> = MutableLiveData()
    val tempCategory : LiveData<String>
        get() = _tempCategory

    private var _category : MutableLiveData<String> = MutableLiveData()
    val category : LiveData<String>
        get() = _category

    var mainIngredient : MutableLiveData<String> = MutableLiveData()

    private val _addTagList : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val addTagList : LiveData<ArrayList<String>>
        get() = _addTagList

    private val _minusTagList : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val minusTagList : LiveData<ArrayList<String>>
        get() = _minusTagList

    var reviewText : MutableLiveData<String> = MutableLiveData()

    private var _privateMode : MutableLiveData<Boolean> = MutableLiveData()
    val privateMode : LiveData<Boolean>
        get() = _privateMode

    private var _reviewImageList : MutableLiveData<MutableList<Uri>> = MutableLiveData()
    val reviewImageList : LiveData<MutableList<Uri>>
        get() = _reviewImageList

    private var _tasteList : MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())
    val tasteList : LiveData<ArrayList<String>>
        get() = _tasteList

    fun setMode(isTurnOn: Boolean) {
        _mode.value = isTurnOn
    }

    fun initCategory(){
        _category.value = "샌드위치"
        if(_mode.value == true){
            _tempCategory.value = "스타벅스"
        }else{
            _tempCategory.value = "샌드위치"
        }
    }

    fun setTempCategory(view : View ){
        view as TextView
        val tempText = view.text.toString()
        _tempCategory.value = tempText
    }

    fun setCategory(){
        _category.value = _tempCategory.value
    }

    fun setAddTagItem(newTagList: ArrayList<String>?){
        _addTagList.value = newTagList
    }

    fun setMinusTagItem(newTagList: ArrayList<String>?){
        _minusTagList.value = newTagList
    }

    fun setPrivateMode(isTurnOn: Boolean) {
        _privateMode.value = isTurnOn
    }

    fun setReviewImageList(newList : MutableList<Uri>){
        _reviewImageList.value = newList
    }

    fun updateTasteList(view : View){
        view as TextView
        val newTaste = view.text.toString()
        if (_tasteList.value?.contains(newTaste) == true){
            _tasteList.value?.remove(newTaste)
        }else{
            _tasteList.value?.add(newTaste)
        }
        _tasteList.value = _tasteList.value
        Log.d("tastList","${_tasteList.value}")
    }

    companion object {
        const val SWEET = "단맛"
        const val SALTY = "짠맛"
        const val SPICY = "매운맛"
        const val BITTER = "쓴맛"
        const val SOUR = "신맛"
    }

}