package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import com.doctor.yumyum.data.remote.response.RankRecipeResponse
import com.doctor.yumyum.domain.repository.MainRepository
import retrofit2.Response

class MainRepositoryImpl : MainRepository {

    private val localDataSource: LocalDataSourceImpl
        get() = LocalDataSourceImpl()
    private val remoteDataSource: RemoteDataSourceImpl
        get() = RemoteDataSourceImpl()

    override fun getMode(): Int? = localDataSource.getMode()

    override fun setMode(mode: Int) = localDataSource.setMode(mode)

    override suspend fun getRecipeRank(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RankRecipeResponse> =
        remoteDataSource.getRecipeRank(categoryName, top, rankDatePeriod)
}