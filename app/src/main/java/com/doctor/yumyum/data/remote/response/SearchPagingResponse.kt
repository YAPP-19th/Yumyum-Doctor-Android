package com.doctor.yumyum.data.remote.response

import com.doctor.yumyum.data.model.RecipeModel
import com.google.gson.annotations.SerializedName

data class SearchPagingResponse(
    @SerializedName("foods") val foods: List<RecipeModel>,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("offset") val offset: Int
)
