package com.doctor.yumyum.data.remote.response

import com.doctor.yumyum.data.model.RecipeModel
import com.google.gson.annotations.SerializedName

data class RankRecipeResponse(
    @SerializedName("topRankingFoods") val topRankingFoods: ArrayList<RecipeModel>
)
