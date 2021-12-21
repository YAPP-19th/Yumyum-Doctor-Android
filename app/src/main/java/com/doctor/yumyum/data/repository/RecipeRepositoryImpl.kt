package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import com.doctor.yumyum.domain.repository.RecipeRepository
import retrofit2.Response

class RecipeRepositoryImpl : RecipeRepository {

    private val remoteDataSource: RemoteDataSourceImpl
        get() = RemoteDataSourceImpl()

    override suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse> =
        remoteDataSource.getRecipeDetail(recipeId)
}