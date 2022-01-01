package com.doctor.yumyum.presentation.ui.researchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import com.doctor.yumyum.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class ResearchListViewModel : BaseViewModel() {
    val repository = RecipeRepositoryImpl()
    private var _sortType: MutableLiveData<Int> = MutableLiveData(SORT_RECENT)
    val sortType: LiveData<Int> get() = _sortType
    private var _tmpSortType: MutableLiveData<Int> = MutableLiveData()
    val tmpSortType: LiveData<Int> get() = _tmpSortType
    private val _errorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> get() = _errorState
    private var _searchType: MutableLiveData<Int> = MutableLiveData(SORT_RECENT)
    val searchType: LiveData<Int> get() = _searchType
    private var _tmpSearchType: MutableLiveData<Int> = MutableLiveData()
    val tmpSearchType: LiveData<Int> get() = _tmpSearchType
    private var _tasteList = MutableLiveData<ArrayList<String>>(ArrayList())
    val tasteList: LiveData<ArrayList<String>> get() = _tasteList
    private val _recipeList = MutableLiveData<ArrayList<RecipeModel>>(ArrayList())
    val recipeList: LiveData<ArrayList<RecipeModel>> get() = _recipeList

    fun initSortType() {
        _tmpSortType.value = sortType.value
    }

    fun setTmpSortType(type: Int) {
        _tmpSortType.value = type
    }

    fun setSortType() {
        _sortType.value = tmpSortType.value
    }

    fun initSearchType() {
        _tmpSearchType.value = SEARCH_HASHTAG
    }

    fun setTmpSearchType(type: Int) {
        _tmpSearchType.value = type
    }

    fun setSearchType() {
        _searchType.value = tmpSearchType.value
    }

    suspend fun searchRecipeList(
        categoryName: String,
        flavors: ArrayList<String>,
        tags: String,
        minPrice: Int?,
        maxPrice: Int?,
        sort: String,
        order: String,
        firstSearchTime: String,
        offset: Int,
        pageSize: Int
    ) {
        try {

            val response: Response<SearchRecipeResponse> =
                repository.searchRecipeList(
                    categoryName,
                    flavors,
                    tags,
                    minPrice,
                    maxPrice,
                    sort,
                    order,
                    firstSearchTime,
                    offset,
                    pageSize
                )

            if (response.isSuccessful) {
                _recipeList.postValue(response.body()?.foods)
            } else {
                _errorState.postValue(ERROR_SEARCH)
            }
        } catch (e: Exception) {
            _errorState.postValue(ERROR_SEARCH)
        }
    }

    fun setBookmarkState(recipe: RecipeModel) {
        viewModelScope.launch {
            try {
                val response: Response<ResponseBody> =
                    if (recipe.isBookmark) repository.deleteBookmark(recipe.id)
                    else repository.postBookmark(recipe.id)

                if (response.isSuccessful) {
                    recipe.isBookmark = !recipe.isBookmark
                } else {
                    _errorState.postValue(ERROR_BOOKMARK)
                }
            } catch (e: Exception) {
                _errorState.postValue(ERROR_BOOKMARK)
            }
        }
    }

    fun setTasteList(tasteList: ArrayList<String>) {
        _tasteList.value = tasteList
    }

    companion object {
        const val SORT_RECENT = 1
        const val SORT_LIKE = 2
        const val SORT_EXPENSIVE = 3
        const val SORT_CHEAP = 4

        const val ERROR_BOOKMARK = R.string.error_bookmark
        const val ERROR_SEARCH = R.string.error_recipe_list

        const val SEARCH_HASHTAG = 11
        const val SEARCH_TASTE = 12
    }
}