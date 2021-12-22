package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.model.NicknamePatchModel
import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import com.doctor.yumyum.data.remote.response.GetNicknameResponse
import com.doctor.yumyum.domain.repository.UserRepository
import okhttp3.ResponseBody
import retrofit2.Response

class UserRepositoryImpl() : UserRepository {
    private val localDataSource: LocalDataSourceImpl
        get() = LocalDataSourceImpl()
    private val remoteDataSource: RemoteDataSourceImpl
        get() = RemoteDataSourceImpl()

    override suspend fun getNickname(): Response<GetNicknameResponse> {
        return remoteDataSource.getNickname()
    }

    override suspend fun validateNickname(nickname: String): Response<ResponseBody> {
        return remoteDataSource.validateNickname(nickname)
    }

    override suspend fun patchNickname(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody> {
        return remoteDataSource.patchNickname(nicknamePatchModel)
    }
}