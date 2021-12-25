package com.doctor.yumyum.presentation.ui.researchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doctor.yumyum.common.base.BaseViewModel
import com.doctor.yumyum.data.model.RankRecipe
import com.doctor.yumyum.data.repository.RecipeRepositoryImpl

class ResearchListViewModel : BaseViewModel() {
    val repository = RecipeRepositoryImpl()
    private var _sortType: MutableLiveData<Int> = MutableLiveData(SORT_RECENT)
    val sortType: LiveData<Int> get() = _sortType
    private var _tmpSortType: MutableLiveData<Int> = MutableLiveData()
    val tmpSortType: LiveData<Int> get() = _tmpSortType

    private val _recipeList = MutableLiveData<ArrayList<RankRecipe>>()
    val recipeList: LiveData<ArrayList<RankRecipe>> get() = _recipeList

    fun initSortType() {
        _tmpSortType.value = sortType.value
    }

    fun setTmpSortType(type: Int) {
        _tmpSortType.value = type
    }

    fun setSortType() {
        _sortType.value = tmpSortType.value
    }

    suspend fun searchRecipeList(
        categoryName: String,
        flavors: String,
        tags: String,
        minPrice: Int,
        maxPrice: Int,
        sort: String,
        order: String,
        firstSearchTime: String,
        offset: Int,
        pageSize: Int
    ) {
        try {

            _recipeList.postValue(
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
                ).body()?.foods
            )
        } catch (e: Exception) {
            e
        }
    }

    companion object {
        const val SORT_RECENT = 1
        const val SORT_SCRAP = 2
        const val SORT_LIKE = 3
        const val SORT_EXPENSIVE = 4
        const val SORT_CHEAP = 5
    }
}