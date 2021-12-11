package com.doctor.yumyum.data.remote

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.NicknamePatchModel
import com.doctor.yumyum.data.model.SignUpModel
import com.doctor.yumyum.data.remote.api.AuthCreationService
import com.doctor.yumyum.data.remote.api.NicknameCreationService
import com.doctor.yumyum.data.remote.api.NicknameService
import com.doctor.yumyum.data.remote.api.NicknameValidationService
import com.doctor.yumyum.data.remote.response.GetNicknameResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteDataSource {
    suspend fun postAuthCreation(signUpModel: SignUpModel): Response<ResponseBody>
    suspend fun getNicknameApi(): Response<GetNicknameResponse>
    suspend fun validateNicknameApi(nickname: String): Response<ResponseBody>
    suspend fun patchNicknameApi(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody>
}

class RemoteDataSourceImpl() : RemoteDataSource {
    override suspend fun postAuthCreation(signUpModel: SignUpModel): Response<ResponseBody> {
        return RetrofitClient.getClient().create(AuthCreationService::class.java)
            .signUp(signUpModel)
    }

    override suspend fun getNicknameApi(): Response<GetNicknameResponse> {
        return RetrofitClient.getClient().create(NicknameCreationService::class.java)
            .getNicknameApi()
    }

    override suspend fun validateNicknameApi(nickname: String): Response<ResponseBody> {
        return RetrofitClient.getClient().create(NicknameValidationService::class.java)
            .validateNicknameApi(nickname)
    }

    override suspend fun patchNicknameApi(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody> {
        return RetrofitClient.getClient().create(NicknameService::class.java)
            .patchNicknameApi(nicknamePatchModel)
    }
}