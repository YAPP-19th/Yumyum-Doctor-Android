package com.doctor.yumyum.data.remote.response

import com.doctor.yumyum.data.model.BestRecipe

data class FavoriteRecipeResponse(
    val favoriteFoods: ArrayList<BestRecipe>
)
