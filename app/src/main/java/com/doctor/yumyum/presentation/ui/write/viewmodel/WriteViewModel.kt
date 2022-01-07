package com.doctor.yumyum.presentation.ui.write.viewmodel

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.common.utils.StatusType
import com.doctor.yumyum.common.utils.TagType
import com.doctor.yumyum.data.model.FoodImage
import com.doctor.yumyum.data.model.TagItem
import com.doctor.yumyum.data.model.WriteRecipe
import com.doctor.yumyum.data.repository.WriteRepositoryImpl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

class WriteViewModel : BaseViewModel() {
    private val writeRepository = WriteRepositoryImpl()
    private var tagList: ArrayList<TagItem> = arrayListOf()
    private var foodStatus: String = ""

    private var _mode: MutableLiveData<Boolean> = MutableLiveData(false)
    val mode: LiveData<Boolean>
        get() = _mode

    private var _tempCategory: MutableLiveData<String> = MutableLiveData()
    val tempCategory: LiveData<String>
        get() = _tempCategory

    private var _category: MutableLiveData<String> = MutableLiveData()
    val category: LiveData<String>
        get() = _category

    var title: MutableLiveData<String> = MutableLiveData()
    var mainIngredient: MutableLiveData<String> = MutableLiveData()

    private val _addTagList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val addTagList: LiveData<ArrayList<String>>
        get() = _addTagList

    private val _minusTagList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val minusTagList: LiveData<ArrayList<String>>
        get() = _minusTagList

    var price: MutableLiveData<Int> = MutableLiveData(0)
    var reviewText: MutableLiveData<String> = MutableLiveData()

    private var _privateMode: MutableLiveData<Boolean> = MutableLiveData()
    val privateMode: LiveData<Boolean>
        get() = _privateMode

    private var _reviewImageList: MutableLiveData<MutableList<Pair<Uri, String>>> = MutableLiveData()
    val reviewImageList: LiveData<MutableList<Pair<Uri, String>>>
        get() = _reviewImageList

    private var _tasteList: MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())
    val tasteList: LiveData<ArrayList<String>>
        get() = _tasteList

    companion object {
        const val SWEET = "단맛"
        const val SALTY = "짠맛"
        const val SPICY = "매운맛"
        const val BITTER = "쓴맛"
        const val SOUR = "신맛"
    }

    fun setMode(isTurnOn: Boolean) {
        _mode.value = isTurnOn
    }

    fun initCategory() {
        if (_mode.value == true) {
            _tempCategory.value = "스타벅스"
        } else {
            _tempCategory.value = "샌드위치"
        }
    }

    fun setTempCategory(view: View) {
        view as TextView
        val tempText = view.text.toString()
        _tempCategory.value = tempText
    }

    fun setCategory() {
        _category.value = _tempCategory.value
    }

    fun setAddTagItem(newTagList: ArrayList<String>?) {
        _addTagList.value = newTagList
    }

    fun setMinusTagItem(newTagList: ArrayList<String>?) {
        _minusTagList.value = newTagList
    }

    fun setPrivateMode(isTurnOn: Boolean) {
        _privateMode.value = isTurnOn
    }

    fun setReviewImageList(newList: MutableList<Pair<Uri, String>>) {
        if(_reviewImageList.value?.size !=3){
            _reviewImageList.value = newList
        }
    }

    fun updateTasteList(view: View) {
        view as TextView
        val newTaste = view.text.toString()
        if (_tasteList.value?.contains(newTaste) == true) {
            _tasteList.value?.remove(newTaste)
        } else {
            _tasteList.value?.add(newTaste)
        }
        _tasteList.value = _tasteList.value
    }

    private fun refactorData() {
        tagList.add(TagItem(null, mainIngredient.value.toString(), TagType.MAIN.name))
        addTagList.value?.forEach {
            tagList.add(TagItem(null, it, TagType.ADD.name))
        }
        minusTagList.value?.forEach {
            tagList.add(TagItem(null, it, TagType.EXTRACT.name))
        }
        foodStatus = if (privateMode.value == true) {
            StatusType.MINE.name
        } else {
            StatusType.SHARED.name
        }
    }

    suspend fun postRecipe() {
        refactorData()
        try {
            val writeRecipe = WriteRecipe(
                categoryName = category.value.toString(),
                title = title.value.toString(),
                tags = tagList,
                price = price.value ?: 0,
                flavors = (tasteList.value?.toList()?:emptyList()),
                reviewMsg = reviewText.value.toString(),
                foodStatus = foodStatus
            )
            val response: Response<ResponseBody> =
                writeRepository.postRecipeText(writeRecipe = writeRecipe)
            val recipeId = (response.headers()["location"]?:"").split("/").last().toInt()
            if (response.isSuccessful) {
                Log.d("WriteViewModel: ","레시피 작성 성공")
                postImages(recipeId)
            }
        } catch (e: Exception) {

        }
    }

    private suspend fun postImages(recipeId: Int) {
        try {
            val images = arrayListOf<MultipartBody.Part>()
            val imageList = _reviewImageList.value ?: emptyList()
            imageList.forEach {
                val file = File(it.second)
                val body = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                images.add(MultipartBody.Part.createFormData(name = "images", filename = file.name, body = body))
            }
            writeRepository.postRecipeImage(recipeId = recipeId,imgList = images)

        } catch (e: Exception) {
            Log.d("WriteViewModel imgPost failed : ",e.toString())
        }
    }

}