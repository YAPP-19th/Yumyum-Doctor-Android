package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.model.NicknamePatchModel
import com.doctor.yumyum.data.model.UserFlavorModel
import com.doctor.yumyum.data.remote.datasource.UserDataSourceImpl
import com.doctor.yumyum.data.remote.response.NicknameResponse
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

    override suspend fun patchNickname(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody> =
        userDataSource.patchNickname(nicknamePatchModel)

    override suspend fun postFlavor(flavorModel: UserFlavorModel): Response<ResponseBody> =
        userDataSource.postFlavor(flavorModel)
}