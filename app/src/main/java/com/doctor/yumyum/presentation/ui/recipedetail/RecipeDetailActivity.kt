package com.doctor.yumyum.presentation.ui.recipedetail

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityRecipeDetailBinding
import com.doctor.yumyum.presentation.adapter.TasteTagAdapter
import com.doctor.yumyum.presentation.adapter.WriteTagAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailActivity :
    BaseActivity<ActivityRecipeDetailBinding>(R.layout.activity_recipe_detail) {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[RecipeDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recipeId = intent.extras?.getInt("recipeId") ?: 1

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recipeDetailRvPlus.adapter = WriteTagAdapter {}
        binding.recipeDetailRvMinus.adapter = WriteTagAdapter {}
        binding.recipeDetailRvTaste.adapter = TasteTagAdapter()
        binding.recipeDetailIbMenu.setOnClickListener {
            RecipeMenuDialog().show(supportFragmentManager, "RecipeMenuDialog")
        }
        binding.recipeDetailIbBack.setOnClickListener {
            finish()
        }

        // 레시피 상세 조회
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getRecipeDetail(recipeId)
        }
    }
}