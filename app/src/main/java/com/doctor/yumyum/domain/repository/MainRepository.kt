package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.remote.response.RankRecipeResponse
import retrofit2.Response

interface MainRepository {

    fun getMode(): Int?

    fun setMode(mode: Int)

    suspend fun getRecipeRank(categoryName: String, top: Int, rankDatePeriod: Int): Response<RankRecipeResponse>
}