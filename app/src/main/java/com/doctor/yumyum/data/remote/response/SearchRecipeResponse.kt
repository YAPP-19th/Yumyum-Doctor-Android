package com.doctor.yumyum.data.remote.response

import com.doctor.yumyum.data.model.RankRecipe
import com.google.gson.annotations.SerializedName

data class SearchRecipeResponse(
    @SerializedName("foods") val foods: ArrayList<RankRecipe>,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("offset") val offset: Int
)
