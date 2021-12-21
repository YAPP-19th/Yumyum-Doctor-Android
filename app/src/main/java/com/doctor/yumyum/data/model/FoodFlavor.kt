package com.doctor.yumyum.data.model

import com.google.gson.annotations.SerializedName

data class FoodFlavor(
    @SerializedName("id") val id: Int,
    @SerializedName("flavorName") val flavorName: String
)
