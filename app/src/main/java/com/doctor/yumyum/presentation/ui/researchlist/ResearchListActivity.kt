package com.doctor.yumyum.presentation.ui.researchlist

import ResearchListAdapter
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityResearchListBinding
import com.doctor.yumyum.databinding.DialogSelectSearchBinding
import com.doctor.yumyum.databinding.DialogSelectSortBinding
import com.doctor.yumyum.presentation.ui.filter.FilterActivity
import com.doctor.yumyum.presentation.ui.recipedetail.RecipeDetailActivity
import com.doctor.yumyum.presentation.ui.search.SearchHashtagActivity
import com.doctor.yumyum.presentation.ui.search.SearchTasteActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class ResearchListActivity :
    BaseActivity<ActivityResearchListBinding>(R.layout.activity_research_list) {

    private lateinit var sortDialog: BottomSheetDialog
    private lateinit var searchDialog: BottomSheetDialog

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
        initSortDialog()
        initSearchDialog()

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
        })
        binding.researchListClAppbar.appbarIbBack.setOnClickListener { finish() }
        binding.researchListRvRecipe.adapter = researchListAdapter
        viewModel.sortType.observe(this) { sortDialog.dismiss() }
        viewModel.searchType.observe(this) {
            if (it == ResearchListViewModel.SEARCH_HASHTAG) {
                startActivity(Intent(this, SearchHashtagActivity::class.java))
            } else if (it == ResearchListViewModel.SEARCH_TASTE) {
                startActivity(Intent(this, SearchTasteActivity::class.java))
            }
            searchDialog.dismiss()
        }
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

    private fun initSortDialog() {
        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_select_sort, null)
        val bottomSheetBinding = DataBindingUtil.inflate<DialogSelectSortBinding>(
            layoutInflater,
            R.layout.dialog_select_sort,
            bottomSheetView as ViewGroup,
            false
        )
        bottomSheetBinding.lifecycleOwner = this
        bottomSheetBinding.viewModel = viewModel
        sortDialog = BottomSheetDialog(this)
        sortDialog.setContentView(bottomSheetBinding.root)
    }

    fun showSortDialog() {
        sortDialog.show()
        viewModel.initSortType()
    }

    private fun initSearchDialog() {
        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_select_search, null)
        val bottomSheetBinding = DataBindingUtil.inflate<DialogSelectSearchBinding>(
            layoutInflater,
            R.layout.dialog_select_search,
            bottomSheetView as ViewGroup,
            false
        )
        bottomSheetBinding.lifecycleOwner = this
        bottomSheetBinding.viewModel = viewModel
        searchDialog = BottomSheetDialog(this)
        searchDialog.setContentView(bottomSheetBinding.root)
    }

    fun showSearchDialog() {
        searchDialog.show()
        viewModel.initSearchType()
    }
}