package com.doctor.yumyum.data.model


import com.google.gson.annotations.SerializedName

data class FavoriteRecipe(
    @SerializedName("categoryName")val categoryName: String,
    @SerializedName("foodImages")val foodImages: List<FoodImage>,
    @SerializedName("foodTitle")val foodTitle: String,
    @SerializedName("id")val id: Int,
    @SerializedName("meFavorite")val meFavorite: Boolean,
    @SerializedName("price")val price: Int,
    @SerializedName("numberOfLikes")val numberOfLikes:Int
)