package com.doctor.yumyum.presentation.ui.recipedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import com.doctor.yumyum.data.repository.RecipeRepositoryImpl
import retrofit2.Response

class RecipeDetailViewModel : BaseViewModel() {
    private val repository = RecipeRepositoryImpl()
    private val _addTagList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val addTagList: LiveData<ArrayList<String>> get() = _addTagList
    private val _minusTagList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val minusTagList: LiveData<ArrayList<String>> get() = _minusTagList
    private val _tasteTagList = MutableLiveData<ArrayList<String>>()
    val tasteTagList: LiveData<ArrayList<String>> get() = _tasteTagList
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorState: LiveData<Boolean> get() = _errorState
    private val _recipeBrand = MutableLiveData<String>()
    val recipeBrand: LiveData<String> get() = _recipeBrand
    private val _recipeTitle: MutableLiveData<String> = MutableLiveData()
    val recipeTitle: LiveData<String> get() = _recipeTitle
    private val _recipePrice = MutableLiveData<Int>(0)
    val recipePrice: LiveData<Int> get() = _recipePrice
    private val _recipeLikeCount = MutableLiveData<Int>(0)
    val recipeLikeCount: LiveData<Int> get() = _recipeLikeCount
    private val _mainMenu = MutableLiveData("")
    val mainMenu: LiveData<String> get() = _mainMenu
    private val _isLike = MutableLiveData<Boolean>(false)
    val isLike: LiveData<Boolean> get() = _isLike
    private val _isBookmark = MutableLiveData<Boolean>(false)
    val isBookmark: LiveData<Boolean> get() = _isBookmark
    private val _authorName = MutableLiveData<String>("")
    val authorName: LiveData<String> get() = _authorName
    private val _content = MutableLiveData<String>()
    val content: LiveData<String> get() = _content

    suspend fun getRecipeDetail(recipeId: Int) {
        try {
            val response: Response<RecipeDetailResponse> = repository.getRecipeDetail(recipeId)

            if (response.isSuccessful) {
                val recipeInfo = response.body()
                _recipeBrand.postValue(recipeInfo?.categoryName)
                _recipeTitle.postValue(recipeInfo?.foodTitle)
                _recipeLikeCount.postValue(recipeInfo?.numberOfLike)
                _isLike.postValue(recipeInfo?.isLike)
                _isBookmark.postValue(recipeInfo?.isBookmark)
                _authorName.postValue("${recipeInfo?.writerName} 학생의 레시피")
                _content.postValue(recipeInfo?.reviewDetail)
                _recipePrice.postValue(recipeInfo?.price)

                val addList: ArrayList<String> = arrayListOf()
                val minusList: ArrayList<String> = arrayListOf()
                val tasteList: ArrayList<String> = arrayListOf()

                val foodTags = recipeInfo?.foodTags
                if (foodTags != null) {
                    for (tag in foodTags) {
                        when (tag.tagUseType) {
                            "MAIN" -> _mainMenu.postValue(tag.name)
                            "ADD" -> addList.add(tag.name)
                            "EXTRACT" -> minusList.add(tag.name)
                        }
                    }
                }
                _addTagList.postValue(addList)
                _minusTagList.postValue(minusList)

                val tasteTags = recipeInfo?.foodFlavors
                if (tasteTags != null) {
                    for (tag in tasteTags) {
                        tasteList.add(tag.flavorName)
                    }
                }
                _tasteTagList.postValue(tasteList)
            }
        } catch (e: Exception) {
            _errorState.postValue(true)
        }
    }
}