package com.doctor.yumyum.presentation.ui.researchrecipe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.common.utils.REQUEST_CODE
import com.doctor.yumyum.databinding.FragmentResearchRecipeBinding
import com.doctor.yumyum.presentation.adapter.RankAdapter
import com.doctor.yumyum.presentation.adapter.ResearchBrandAdapter
import com.doctor.yumyum.presentation.ui.login.ErrorDialog
import com.doctor.yumyum.presentation.ui.recipedetail.RecipeDetailActivity
import com.doctor.yumyum.presentation.ui.researchlist.ResearchListActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResearchRecipeFragment :
    BaseFragment<FragmentResearchRecipeBinding>(R.layout.fragment_research_recipe) {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ResearchRecipeViewModel::class.java]
    }
    private val beverageBrandList: MutableList<Pair<Int, String>> by lazy {
        mutableListOf(
            Pair(R.drawable.ic_brand_starbucks, getString(R.string.common_starbucks)),
            Pair(R.drawable.ic_brand_amasvin, getString(R.string.common_amasvin)),
            Pair(R.drawable.ic_brand_gongcha, getString(R.string.common_gongcha)),
            Pair(R.drawable.ic_brand_ediya, getString(R.string.common_ediya)),
            Pair(R.drawable.ic_brand_cocktail, getString(R.string.common_cocktail)),
            Pair(R.drawable.ic_brand_etc, getString(R.string.common_etc))
        )
    }
    private val foodBrandList: MutableList<Pair<Int, String>> by lazy {
        mutableListOf(
            Pair(R.drawable.ic_brand_sandwich, getString(R.string.common_sandwich)),
            Pair(R.drawable.ic_brand_maratang, getString(R.string.common_maratang)),
            Pair(R.drawable.ic_brand_tteokbokki, getString(R.string.common_tteokbokki)),
            Pair(R.drawable.ic_brand_salad, getString(R.string.common_salad)),
            Pair(R.drawable.ic_brand_yogurt, getString(R.string.common_yogurt)),
            Pair(R.drawable.ic_brand_ramen, getString(R.string.common_ramen))
        )
    }
    private lateinit var brandRecyclerAdapter: ResearchBrandAdapter
    private val detailLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == REQUEST_CODE.DELETE_RECIPE) {
                getRankRecipes()
            }
        }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.researchRecipeRecyclerviewRanking.adapter = RankAdapter { recipeId ->
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra("recipeId", recipeId)
            detailLauncher.launch(intent)
        }

        // 브랜드 아이템 클릭시 이동
        brandRecyclerAdapter = ResearchBrandAdapter {
            val intent = Intent(context, ResearchListActivity::class.java)
            intent.putExtra(getString(R.string.common_brand_en), it)
            startActivity(intent)
        }
        binding.researchRecipeRecyclerviewBrand.adapter = brandRecyclerAdapter
        brandRecyclerAdapter.setBrandList(beverageBrandList)
        brandRecyclerAdapter.notifyDataSetChanged()

        // 모드 변경에 따른 switch 이미지 설정
        viewModel.mode.observe(viewLifecycleOwner) { mode ->
            binding.researchRecipeSwMode.trackTintList = ResourcesCompat.getColorStateList(
                requireContext().resources,
                if (mode == R.string.common_food) R.color.main_orange else R.color.sub_green,
                null
            )
            binding.researchRecipeSwMode.setThumbResource(if (mode == R.string.common_food) R.drawable.sw_mode_thumb_food else R.drawable.sw_mode_thumb_drink)

            changeBrandList(mode)
            // 주간 랭킹 리스트 조회
            getRankRecipes()
        }

        viewModel.errorState.observe(viewLifecycleOwner) { resId ->
            showToast(getString(resId))
            ErrorDialog().show(parentFragmentManager, "ResearchRecipeFragment")
        }

        return binding.root
    }

    // 주간 랭킹 리스트 조회
    private fun getRankRecipes() = CoroutineScope(Dispatchers.IO).launch {
        viewModel.getRankRecipe(
            getString(viewModel.mode.value ?: R.string.common_food),
            top = 9,
            rankDatePeriod = 7
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeBrandList(mode: Int) {
        if (mode == R.string.common_beverage) {
            brandRecyclerAdapter.setBrandList(beverageBrandList)
        } else {
            brandRecyclerAdapter.setBrandList(foodBrandList)
        }
        brandRecyclerAdapter.notifyDataSetChanged()
    }
}