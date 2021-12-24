package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.model.UserFlavorModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserFlavorService {

    @PUT("/api/v1/users/me/flavors")
    suspend fun postFlavor(
        @Body userFlavorModel: UserFlavorModel
    ): Response<ResponseBody>
}