package com.doctor.yumyum.data.model

import com.google.gson.annotations.SerializedName

data class TagItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("tagUseType") val tagUseType: String
)
