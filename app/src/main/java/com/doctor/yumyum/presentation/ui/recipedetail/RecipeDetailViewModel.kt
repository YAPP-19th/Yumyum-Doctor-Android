package com.doctor.yumyum.presentation.ui.recipedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.FoodImage
import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import com.doctor.yumyum.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class RecipeDetailViewModel : BaseViewModel() {
    private val repository = RecipeRepositoryImpl()
    private val _addTagList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val addTagList: LiveData<ArrayList<String>> get() = _addTagList
    private val _minusTagList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val minusTagList: LiveData<ArrayList<String>> get() = _minusTagList
    private val _tasteTagList = MutableLiveData<ArrayList<String>>()
    val tasteTagList: LiveData<ArrayList<String>> get() = _tasteTagList
    private val _errorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> get() = _errorState
    private val _recipeBrand = MutableLiveData<String>()
    val recipeBrand: LiveData<String> get() = _recipeBrand
    private val _recipeTitle: MutableLiveData<String> = MutableLiveData()
    val recipeTitle: LiveData<String> get() = _recipeTitle
    private val _recipePrice = MutableLiveData(0)
    val recipePrice: LiveData<Int> get() = _recipePrice
    private val _recipeLikeCount = MutableLiveData(0)
    val recipeLikeCount: LiveData<Int> get() = _recipeLikeCount
    private val _mainMenu = MutableLiveData("")
    val mainMenu: LiveData<String> get() = _mainMenu
    private val _isLike = MutableLiveData(false)
    val isLike: LiveData<Boolean> get() = _isLike
    private val _isBookmark = MutableLiveData(false)
    val isBookmark: LiveData<Boolean> get() = _isBookmark
    private val _authorName = MutableLiveData("")
    val authorName: LiveData<String> get() = _authorName
    private val _content = MutableLiveData<String>()
    val content: LiveData<String> get() = _content
    private val _recipeId = MutableLiveData<Int>()
    val recipeId: MutableLiveData<Int> get() = _recipeId
    private val _imageList = MutableLiveData<ArrayList<FoodImage>>(arrayListOf())
    val imageList: LiveData<ArrayList<FoodImage>> get() = _imageList
    private val _isMyFood: MutableLiveData<Boolean> = MutableLiveData()
    val isMyFood: LiveData<Boolean>
        get() = _isMyFood

    suspend fun getRecipeDetail(recipeId: Int) {
        _recipeId.postValue(recipeId)

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

                val imageList: List<FoodImage> = if (recipeInfo?.foodImages?.isEmpty() == false)
                    recipeInfo.foodImages
                else
                    listOf(FoodImage("id", "sample", "sample"))
                _imageList.postValue(ArrayList(imageList))

                _isMyFood.postValue(recipeInfo?.myFood)

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
            } else {
                _errorState.postValue(ERROR_RECIPE_DETAIL)
            }
        } catch (e: Exception) {
            _errorState.postValue(ERROR_RECIPE_DETAIL)
        }
    }

    fun setLikeState(recipeId: Int) {
        viewModelScope.launch {
            try {
                val response: Response<ResponseBody> =
                    if (isLike.value == true) repository.deleteLike(recipeId)
                    else repository.postLike(recipeId)

                if (response.isSuccessful) {
                    if (isLike.value == true) _isLike.postValue(false)
                    else _isLike.postValue(true)
                } else {
                    _errorState.postValue(ERROR_LIKE)
                }
            } catch (e: Exception) {
                _errorState.postValue(ERROR_LIKE)
            }
        }
    }

    fun setBookmarkState(recipeId: Int) {
        viewModelScope.launch {
            try {
                val response: Response<ResponseBody> =
                    if (isBookmark.value == true) repository.deleteBookmark(recipeId)
                    else repository.postBookmark(recipeId)

                if (response.isSuccessful) {
                    if (isBookmark.value == true) _isBookmark.postValue(false)
                    else _isBookmark.postValue(true)
                } else {
                    _errorState.postValue(ERROR_BOOKMARK)
                }
            } catch (e: Exception) {
                _errorState.postValue(ERROR_BOOKMARK)
            }
        }
    }

    fun deleteRecipe(recipeId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteRecipe(recipeId)
            } catch (e: java.lang.Exception) {
                _errorState.postValue(ERROR_DELETE_RECIPE)
            }
        }
    }

    companion object {
        const val ERROR_RECIPE_DETAIL = R.string.error_recipe_detail
        const val ERROR_LIKE = R.string.error_like
        const val ERROR_BOOKMARK = R.string.error_bookmark

        const val ERROR_DELETE_RECIPE = R.string.error_delete_recipe
    }
}