package com.doctor.yumyum.data.model

import com.google.gson.annotations.SerializedName

data class RankRecipe(
    @SerializedName("foodName") val foodName: String,
    @SerializedName("thumnailImgUrl") val thumbnailImgUrl: String
)
