package com.doctor.yumyum.data.remote.response

import com.doctor.yumyum.data.model.RecipeModel
import java.io.Serializable

data class RecipeRecommendationResponse(
    val recommendationFoods: ArrayList<RecipeModel>
):Serializable
