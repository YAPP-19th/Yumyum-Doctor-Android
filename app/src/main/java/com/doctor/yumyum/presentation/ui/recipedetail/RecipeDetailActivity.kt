package com.doctor.yumyum.presentation.ui.recipedetail

import android.os.Bundle
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityRecipeDetailBinding

class RecipeDetailActivity : BaseActivity<ActivityRecipeDetailBinding>(R.layout.activity_recipe_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recipeId = intent.extras?.getInt("recipeId")

        binding.recipeDetailIbMenu.setOnClickListener {
            RecipeMenuDialog().show(supportFragmentManager, "RecipeMenuDialog")
        }
        binding.recipeDetailIbBack.setOnClickListener {
            finish()
        }
    }
}