package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import com.doctor.yumyum.domain.repository.RecipeRepository
import okhttp3.ResponseBody
import retrofit2.Response

class RecipeRepositoryImpl : RecipeRepository {

    private val remoteDataSource: RemoteDataSourceImpl
        get() = RemoteDataSourceImpl()

    override suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse> =
        remoteDataSource.getRecipeDetail(recipeId)

    override suspend fun postLike(recipeId: Int): Response<ResponseBody> =
        remoteDataSource.postLike(recipeId)

    override suspend fun deleteLike(recipeId: Int): Response<ResponseBody> =
        remoteDataSource.deleteLike(recipeId)

    override suspend fun postBookmark(recipeId: Int): Response<ResponseBody> =
        remoteDataSource.postBookmark(recipeId)

    override suspend fun deleteBookmark(recipeId: Int): Response<ResponseBody> =
        remoteDataSource.deleteBookmark(recipeId)
}