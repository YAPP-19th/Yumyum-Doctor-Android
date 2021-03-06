package com.doctor.yumyum.presentation.ui.recipedetail

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.common.utils.REQUEST_CODE
import com.doctor.yumyum.common.utils.dpToIntPx
import com.doctor.yumyum.databinding.ActivityRecipeDetailBinding
import com.doctor.yumyum.presentation.adapter.RecipeDetailAdapter
import com.doctor.yumyum.presentation.adapter.TasteTagAdapter
import com.doctor.yumyum.presentation.adapter.WriteTagAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

        setLayoutHeight()

        val recipeId = intent.extras?.getInt("recipeId") ?: 1

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recipeDetailRvPlus.adapter = WriteTagAdapter {}
        binding.recipeDetailRvMinus.adapter = WriteTagAdapter {}
        binding.recipeDetailRvTaste.adapter = TasteTagAdapter()
        binding.recipeDetailIbMenu.setOnClickListener {
            RecipeMenuDialog(recipeId).show(supportFragmentManager, "RecipeMenuDialog")
        }
        binding.recipeDetailIbBack.setOnClickListener { finish() }
        binding.recipeDetailIbDelete.setOnClickListener {
            RecipeDeleteDialog{
                showLoading()
                lifecycleScope.launch {
                    val deleteRecipe = lifecycleScope.async {
                        viewModel.deleteRecipe(recipeId)
                    }
                    deleteRecipe.await()
                    setResult(REQUEST_CODE.DELETE_RECIPE,intent)
                    finish()
                }
            }.show(supportFragmentManager,"DeleteRecipe")
        }

        // ????????? ?????? ??????
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getRecipeDetail(recipeId)
        }

        viewModel.errorState.observe(this) { resId ->
            showToast(getString(resId))
        }

        // ????????? ????????? ???????????? ??????
        binding.recipeDetailVpImage.adapter = RecipeDetailAdapter()
    }

    private fun setLayoutHeight() {
        // weight ????????? ?????? ????????? ??? height ??????
        val displayMetrics = DisplayMetrics()
        this.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val imageHeight = (displayMetrics.widthPixels * 0.8).toInt()
        binding.recipeDetailVpImage.layoutParams.height = imageHeight

        // ????????? ??? height ??? ?????? ?????? ?????? content top padding ??????
        val paddingTop = imageHeight - dpToIntPx(62f)
        binding.recipeDetailSvContent.setPadding(0, paddingTop, 0, 0)
    }
}