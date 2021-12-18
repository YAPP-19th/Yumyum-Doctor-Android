package com.doctor.yumyum.data.remote.response

import com.doctor.yumyum.data.model.RankRecipe
import com.google.gson.annotations.SerializedName

data class RankRecipeResponse(
    @SerializedName("topRankingFoods") val topRankingFoods: List<RankRecipe>
)
