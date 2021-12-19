package com.doctor.yumyum.data.remote

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.NicknamePatchModel
import com.doctor.yumyum.data.model.SignUpModel
import com.doctor.yumyum.data.remote.api.AuthService
import com.doctor.yumyum.data.remote.api.UserService
import com.doctor.yumyum.data.remote.response.GetNicknameResponse
import com.doctor.yumyum.data.remote.api.RankRecipeService
import com.doctor.yumyum.data.remote.api.RecipeService
import com.doctor.yumyum.data.remote.response.RankRecipeResponse
import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteDataSource {
    suspend fun signUp(signUpModel: SignUpModel): Response<ResponseBody>
    suspend fun getNickname(): Response<GetNicknameResponse>
    suspend fun validateNickname(nickname: String): Response<ResponseBody>
    suspend fun patchNickname(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody>
    suspend fun getRecipeRank(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RankRecipeResponse>

    suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse>
}

class RemoteDataSourceImpl() : RemoteDataSource {
    override suspend fun signUp(signUpModel: SignUpModel): Response<ResponseBody> {
        return RetrofitClient.getClient().create(AuthService::class.java)
            .signUp(signUpModel)
    }

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

    override suspend fun getRecipeDetail(
        recipeId: Int
    ): Response<RecipeDetailResponse> =
        RetrofitClient.getClient().create(RecipeService::class.java).getRecipeDetail(recipeId)
}