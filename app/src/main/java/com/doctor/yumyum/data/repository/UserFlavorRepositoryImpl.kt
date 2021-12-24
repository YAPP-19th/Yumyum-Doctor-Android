package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.model.UserFlavorModel
import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import com.doctor.yumyum.domain.repository.UserFlavorRepository
import okhttp3.ResponseBody
import retrofit2.Response

class UserFlavorRepositoryImpl : UserFlavorRepository{
    private val remoteDataSource: RemoteDataSourceImpl
        get() = RemoteDataSourceImpl()

    override suspend fun putFlavor(userFlavorModel: UserFlavorModel): Response<ResponseBody> {
        return remoteDataSource.postFlavor(userFlavorModel)
    }
}