package com.doctor.yumyum.data.remote.datasource

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.remote.api.MyRecipeService
import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.create


interface MyRecipeDataSource {
    suspend fun getMyRecipe(
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
        mineFoodType : String,
        status : String?
    ): Response<SearchRecipeResponse>

    suspend fun deleteFavorite(recipeId : Int) : Response<ResponseBody>
}


class MyRecipeDataSourceImpl : MyRecipeDataSource {
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
        RetrofitClient.getClient().create(MyRecipeService::class.java)
            .getMyRecipeList(
                categoryName = categoryName,
                flavors = flavors,
                tags = tags,
                minPrice = minPrice,
                maxPrice = maxPrice,
                sort = sort,
                order = order,
                firstSearchTime = firstSearchTime,
                pageSize = pageSize,
                offSet = offset,
                mineFoodType = mineFoodType,
                status = status,
            )

    override suspend fun deleteFavorite(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(MyRecipeService::class.java)
            .deleteFavorite(recipeId)

}