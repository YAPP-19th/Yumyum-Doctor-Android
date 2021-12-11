package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.model.NicknamePatchModel
import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import com.doctor.yumyum.data.remote.response.GetNicknameResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface UserRepository {
    val localDataSource: LocalDataSourceImpl
    val remoteDataSource: RemoteDataSourceImpl

    suspend fun getNicknameApi(): Response<GetNicknameResponse>
    suspend fun validateNicknameApi(nickname: String): Response<ResponseBody>
    suspend fun patchNicknameApi(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody>
}