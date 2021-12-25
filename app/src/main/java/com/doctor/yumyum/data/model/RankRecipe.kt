package com.doctor.yumyum.data.model

import com.google.gson.annotations.SerializedName

data class RankRecipe(
    @SerializedName("id") val id: Int,
    @SerializedName("foodTitle") val foodName: String,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("price") val price: Int,
    @SerializedName("numberOfLikes") val numberOfLikes: Int,
    @SerializedName("foodimages") val foodImages: ArrayList<FoodImage>,
    @SerializedName("meLike") val isLike: Boolean,
    @SerializedName("meBookmark") var isBookmark: Boolean,
    @SerializedName("meFavorite") val isFavorite: Boolean
)
