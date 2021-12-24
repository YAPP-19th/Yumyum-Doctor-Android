package com.doctor.yumyum.data.remote

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.NicknamePatchModel
import com.doctor.yumyum.data.model.SignInModel
import com.doctor.yumyum.data.model.SignUpModel
import com.doctor.yumyum.data.model.UserFlavorModel
import com.doctor.yumyum.data.remote.api.AuthService
import com.doctor.yumyum.data.remote.api.UserService
import com.doctor.yumyum.data.remote.response.GetNicknameResponse
import com.doctor.yumyum.data.remote.api.RankRecipeService
import com.doctor.yumyum.data.remote.api.UserFlavorService
import com.doctor.yumyum.data.remote.api.RecipeService
import com.doctor.yumyum.data.remote.response.RankRecipeResponse
import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
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

    suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse>
    suspend fun postLike(recipeId: Int): Response<ResponseBody>
    suspend fun deleteLike(recipeId: Int): Response<ResponseBody>
    suspend fun postBookmark(recipeId: Int): Response<ResponseBody>
    suspend fun deleteBookmark(recipeId: Int): Response<ResponseBody>
    suspend fun putFlavor(flavorModel: UserFlavorModel): Response<ResponseBody>
}

class RemoteDataSourceImpl : RemoteDataSource {
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

    override suspend fun putFlavor(userFlavorModel: UserFlavorModel): Response<ResponseBody> =
        RetrofitClient.getClient().create(UserFlavorService::class.java)
            .putFlavor(userFlavorModel)

    override suspend fun getRecipeDetail(
        recipeId: Int
    ): Response<RecipeDetailResponse> =
        RetrofitClient.getClient().create(RecipeService::class.java).getRecipeDetail(recipeId)

    override suspend fun postLike(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).postLike(recipeId)

    override suspend fun deleteLike(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).deleteLike(recipeId)

    override suspend fun postBookmark(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).postBookmark(recipeId)

    override suspend fun deleteBookmark(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).deleteBookmark(recipeId)
}