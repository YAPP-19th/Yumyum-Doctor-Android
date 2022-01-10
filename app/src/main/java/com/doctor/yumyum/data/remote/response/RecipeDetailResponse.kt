package com.doctor.yumyum.data.remote.response

import com.doctor.yumyum.data.model.FoodFlavor
import com.doctor.yumyum.data.model.FoodImage
import com.doctor.yumyum.data.model.TagItem
import com.google.gson.annotations.SerializedName

data class RecipeDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("foodTitle") val foodTitle: String,
    @SerializedName("reviewDetail") val reviewDetail: String,
    @SerializedName("price") val price: Int,
    @SerializedName("numberOfLike") val numberOfLike: Int,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("writerName") val writerName: String,
    @SerializedName("foodTags") val foodTags: List<TagItem>,
    @SerializedName("foodFlavors") val foodFlavors: List<FoodFlavor>,
    @SerializedName("foodImages") val foodImages: List<FoodImage>,
    @SerializedName("meBookmark") val isBookmark: Boolean,
    @SerializedName("meLike") val isLike: Boolean,
    @SerializedName("myFood") val myFood : Boolean
)