package com.doctor.yumyum.data.remote.datasource

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.data.remote.api.RankRecipeService
import com.doctor.yumyum.data.remote.api.RecipeService
import com.doctor.yumyum.data.remote.response.*
import okhttp3.ResponseBody
import retrofit2.Response

interface RecipeDataSource {
    suspend fun getRecipeRank(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RankRecipeResponse>

    suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse>
    suspend fun postLike(recipeId: Int): Response<ResponseBody>
    suspend fun deleteLike(recipeId: Int): Response<ResponseBody>
    suspend fun postBookmark(recipeId: Int): Response<ResponseBody>
    suspend fun deleteBookmark(recipeId: Int): Response<ResponseBody>
    suspend fun searchRecipeList(
        categoryName: String,
        flavors: ArrayList<String>,
        tags: ArrayList<String>,
        minPrice: Int?,
        maxPrice: Int?,
        sort: String,
        order: String,
        firstSearchTime: String,
        offset: Int,
        pageSize: Int
    ): Response<SearchRecipeResponse>

    suspend fun getFavorite(categoryName: String): Response<FavoriteRecipeResponse>
    suspend fun getRecommendation(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RecipeRecommendationResponse>

    suspend fun searchPagingList(
        categoryName: String,
        flavors: ArrayList<String>,
        tags: ArrayList<String>,
        minPrice: Int?,
        maxPrice: Int?,
        sort: String,
        order: String,
        firstSearchTime: String,
        offset: Int,
        pageSize: Int
    ): LiveData<PagingData<RecipeModel>>
}

class RecipeDataSourceImp : RecipeDataSource {
    override suspend fun getRecipeRank(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RankRecipeResponse> =
        RetrofitClient.getClient().create(RankRecipeService::class.java)
            .getRecipeRank(categoryName, top, rankDatePeriod)

    override suspend fun getRecipeDetail(
        recipeId: Int
    ): Response<RecipeDetailResponse> =
        RetrofitClient.getClient().create(RecipeService::class.java).getRecipeDetail(recipeId)

    override suspend fun postLike(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).postLike(recipeId)

    override suspend fun deleteLike(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).deleteLike(recipeId)

    override suspend fun postBookmark(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).postBookmark(recipeId)

    override suspend fun deleteBookmark(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).deleteBookmark(recipeId)

    override suspend fun searchRecipeList(
        categoryName: String,
        flavors: ArrayList<String>,
        tags: ArrayList<String>,
        minPrice: Int?,
        maxPrice: Int?,
        sort: String,
        order: String,
        firstSearchTime: String,
        offset: Int,
        pageSize: Int
    ): Response<SearchRecipeResponse> =
        RetrofitClient.getClient().create(RecipeService::class.java)
            .searchRecipeList(
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

    override suspend fun getFavorite(categoryName: String): Response<FavoriteRecipeResponse> =
        RetrofitClient.getClient().create(RecipeService::class.java).getFavoriteList(categoryName)

    override suspend fun getRecommendation(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RecipeRecommendationResponse> =
        RetrofitClient.getClient().create(RecipeService::class.java)
            .getRecommendation(categoryName, top, rankDatePeriod)

    override suspend fun searchPagingList(
        categoryName: String,
        flavors: ArrayList<String>,
        tags: ArrayList<String>,
        minPrice: Int?,
        maxPrice: Int?,
        sort: String,
        order: String,
        firstSearchTime: String,
        offset: Int,
        pageSize: Int
    ): LiveData<PagingData<RecipeModel>> =
        Pager(PagingConfig(pageSize = pageSize)) {
            SearchPagingSource(
                RetrofitClient.getClient().create(RecipeService::class.java),
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
        }.liveData
}

