package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.model.WriteRecipe
import com.doctor.yumyum.data.remote.datasource.WriteDataSourceImpl
import com.doctor.yumyum.domain.repository.WriteRepository
import okhttp3.ResponseBody
import retrofit2.Response

class WriteRepositoryImpl : WriteRepository {
    private val writeDataSource : WriteDataSourceImpl
        get() = WriteDataSourceImpl()

    override suspend fun postRecipeText(writeRecipe: WriteRecipe): Response<ResponseBody> =
        writeDataSource.postRecipeText(writeRecipe)

}