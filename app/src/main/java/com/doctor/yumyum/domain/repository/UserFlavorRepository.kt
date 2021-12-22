package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.model.UserFlavorModel
import com.doctor.yumyum.data.remote.RemoteDataSourceImpl
import okhttp3.ResponseBody
import retrofit2.Response

interface UserFlavorRepository {
    suspend fun putFlavor(userFlavorModel: UserFlavorModel):Response<ResponseBody>
}