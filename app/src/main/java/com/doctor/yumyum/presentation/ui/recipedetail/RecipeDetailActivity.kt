package com.doctor.yumyum.presentation.ui.recipedetail

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.common.utils.dpToIntPx
import com.doctor.yumyum.databinding.ActivityRecipeDetailBinding
import com.doctor.yumyum.presentation.adapter.RecipeDetailAdapter
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

        setLayoutHeight()

        val recipeId = intent.extras?.getInt("recipeId") ?: 1

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recipeDetailRvPlus.adapter = WriteTagAdapter {}
        binding.recipeDetailRvMinus.adapter = WriteTagAdapter {}
        binding.recipeDetailRvTaste.adapter = TasteTagAdapter()
        binding.recipeDetailIbMenu.setOnClickListener {
            RecipeMenuDialog().show(supportFragmentManager, "RecipeMenuDialog")
        }
        binding.recipeDetailIbBack.setOnClickListener { finish() }

        // 레시피 상세 조회
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getRecipeDetail(recipeId)
        }

        viewModel.errorState.observe(this) { resId ->
            showToast(getString(resId))
        }

        // 레시피 이미지 뷰페이저 설정
        binding.recipeDetailVpImage.adapter = RecipeDetailAdapter()
    }

    private fun setLayoutHeight() {
        // weight 길이에 따른 이미지 뷰 height 설정
        val displayMetrics = DisplayMetrics()
        this.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val imageHeight = (displayMetrics.widthPixels * 0.8).toInt()
        binding.recipeDetailVpImage.layoutParams.height = imageHeight

        // 이미지 뷰 height 에 따른 상세 화면 content top padding 설정
        val paddingTop = imageHeight - dpToIntPx(62f)
        binding.recipeDetailSvContent.setPadding(0, paddingTop, 0, 0)
    }
}