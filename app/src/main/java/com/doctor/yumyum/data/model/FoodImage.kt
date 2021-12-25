package com.doctor.yumyum.data.model

import com.google.gson.annotations.SerializedName

data class FoodImage(
    @SerializedName("id") val id: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("realImageName") val realImageName: String
)
