package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.remote.datasource.MyRecipeDataSourceImpl
import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import com.doctor.yumyum.domain.repository.MyRecipeRepository
import retrofit2.Response

class MyRecipeRepositoryImpl : MyRecipeRepository {
    private val myRecipeDataSource : MyRecipeDataSourceImpl
        get() = MyRecipeDataSourceImpl()


    override suspend fun getMyRecipe(
        categoryName: String,
        flavors: String,
        tags: String,
        minPrice: Int?,
        maxPrice: Int?,
        sort: String,
        order: String,
        offset: Int,
        pageSize: Int,
        firstSearchTime : String,
        mineFoodType: String,
        status: String?
    ): Response<SearchRecipeResponse> =
        myRecipeDataSource.getMyRecipe(
            categoryName = categoryName,
            flavors = flavors,
            tags = tags,
            minPrice = minPrice,
            maxPrice = maxPrice,
            sort = sort,
            order = order,
            pageSize = pageSize,
            firstSearchTime = firstSearchTime,
            mineFoodType = mineFoodType,
            status = status,
            offset = offset
        )

}