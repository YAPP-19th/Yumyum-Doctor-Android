package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.model.UserFlavor
import com.doctor.yumyum.data.remote.datasource.UserDataSourceImpl
import com.doctor.yumyum.data.remote.response.NicknameResponse
import com.doctor.yumyum.data.remote.response.UserInfoResponse
import com.doctor.yumyum.domain.repository.UserRepository
import okhttp3.ResponseBody
import retrofit2.Response

class UserRepositoryImpl() : UserRepository {
    private val userDataSource: UserDataSourceImpl
        get() = UserDataSourceImpl()

    override suspend fun getNickname(): Response<NicknameResponse> =
        userDataSource.getNickname()

    override suspend fun validateNickname(nickname: String): Response<ResponseBody> =
        userDataSource.validateNickname(nickname)

    override suspend fun patchNickname(nickname: String): Response<ResponseBody> =
        userDataSource.patchNickname(nickname)

    override suspend fun postFlavor(flavor: UserFlavor): Response<ResponseBody> =
        userDataSource.postFlavor(flavor)

    override suspend fun getUserInfo(): Response<UserInfoResponse> =
        userDataSource.getUserInfo()
}