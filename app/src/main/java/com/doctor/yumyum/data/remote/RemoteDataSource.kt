package com.doctor.yumyum.data.remote

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.NicknamePatchModel
import com.doctor.yumyum.data.model.SignInModel
import com.doctor.yumyum.data.model.SignUpModel
import com.doctor.yumyum.data.remote.api.AuthService
import com.doctor.yumyum.data.remote.api.UserService
import com.doctor.yumyum.data.remote.response.GetNicknameResponse
import com.doctor.yumyum.data.remote.api.RankRecipeService
import com.doctor.yumyum.data.remote.response.RankRecipeResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteDataSource {
    suspend fun signUp(signUpModel: SignUpModel): Response<ResponseBody>
    suspend fun signIn(signInModel: SignInModel): Response<ResponseBody>
    suspend fun getNickname(): Response<GetNicknameResponse>
    suspend fun validateNickname(nickname: String): Response<ResponseBody>
    suspend fun patchNickname(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody>
    suspend fun getRecipeRank(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RankRecipeResponse>
}

class RemoteDataSourceImpl() : RemoteDataSource {
    override suspend fun signUp(signUpModel: SignUpModel): Response<ResponseBody> =
        RetrofitClient.getClient().create(AuthService::class.java)
            .signUp(signUpModel)

    override suspend fun signIn(signInModel: SignInModel): Response<ResponseBody> =
        RetrofitClient.getClient().create(AuthService::class.java).signIn(signInModel)

    override suspend fun getNickname(): Response<GetNicknameResponse> {
        return RetrofitClient.getClient().create(UserService::class.java)
            .getNickname()
    }

    override suspend fun validateNickname(nickname: String): Response<ResponseBody> {
        return RetrofitClient.getClient().create(UserService::class.java)
            .validateNickname(nickname)
    }

    override suspend fun patchNickname(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody> {
        return RetrofitClient.getClient().create(UserService::class.java)
            .patchNickname(nicknamePatchModel)
    }

    override suspend fun getRecipeRank(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RankRecipeResponse> =
        RetrofitClient.getClient().create(RankRecipeService::class.java)
            .getRecipeRank(categoryName, top, rankDatePeriod)
}