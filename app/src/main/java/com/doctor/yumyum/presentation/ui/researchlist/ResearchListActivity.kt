package com.doctor.yumyum.presentation.ui.researchlist

import ResearchListAdapter
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.common.utils.dpToIntPx
import com.doctor.yumyum.databinding.ActivityResearchListBinding
import com.doctor.yumyum.databinding.DialogSelectSortBinding
import com.doctor.yumyum.presentation.ui.filter.FilterActivity
import com.doctor.yumyum.presentation.ui.login.ErrorDialog
import com.doctor.yumyum.presentation.ui.recipedetail.RecipeDetailActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class ResearchListActivity :
    BaseActivity<ActivityResearchListBinding>(R.layout.activity_research_list) {

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ResearchListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoryName = intent.extras?.get(getString(R.string.common_brand_en)).toString()
        binding.activity = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.researchListTvBrand.text = categoryName
        initDialog()

        // 필터 화면으로 이동
        binding.researchListTvFilter.setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
        }
        val researchListAdapter = ResearchListAdapter({ recipeId ->
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("recipeId", recipeId)
            startActivity(intent)
        }, { recipe ->
            viewModel.setBookmarkState(recipe)
        }, calculateImageHeight())
        binding.researchListRvRecipe.adapter = researchListAdapter
        viewModel.sortType.observe(this) { bottomSheetDialog.dismiss() }
        lifecycleScope.launch {
            viewModel.searchRecipeList(
                categoryName,
                "",
                "",
                0,
                100000,
                "like",
                "asc",
                "2021-12-26T12:12:12",
                0,
                10
            )
        }

        viewModel.recipeList.observe(this) {
            researchListAdapter.setRecipeList(it)
        }
        viewModel.errorState.observe(this) { resId ->
            showToast(getString(resId))
        }
    }

    private fun initDialog() {
        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_select_sort, null)
        val bottomSheetBinding = DataBindingUtil.inflate<DialogSelectSortBinding>(
            layoutInflater,
            R.layout.dialog_select_sort,
            bottomSheetView as ViewGroup,
            false
        )
        bottomSheetBinding.lifecycleOwner = this
        bottomSheetBinding.viewModel = viewModel
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
    }

    fun showBottomSheet() {
        bottomSheetDialog.show()
        viewModel.initSortType()
    }

    private fun calculateImageHeight(): Int {
        // weight 길이에 따른 이미지 뷰 height 설정
        val displayMetrics = DisplayMetrics()
        this.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val imageWidth = (displayMetrics.widthPixels - dpToIntPx(16f * 3)) / 2
        return (imageWidth * 0.8).toInt()
    }
}