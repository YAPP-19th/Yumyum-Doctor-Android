package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import com.doctor.yumyum.data.remote.response.GetNicknameResponse
import com.doctor.yumyum.domain.repository.UserRepository
import okhttp3.ResponseBody
import retrofit2.Response

class UserRepositoryImpl() : UserRepository {
    override val localDataSource: LocalDataSourceImpl
        get() = LocalDataSourceImpl()
    override val remoteDataSource: RemoteDataSourceImpl
        get() = RemoteDataSourceImpl()

    override suspend fun getNicknameApi(): Response<GetNicknameResponse> {
        return remoteDataSource.getNicknameApi()
    }

    override suspend fun validateNicknameApi(nickname: String): Response<ResponseBody> {
        return remoteDataSource.validateNicknameApi(nickname)
    }
}