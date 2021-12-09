package com.doctor.yumyum.presentation.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentResearchRecipeBinding
import com.doctor.yumyum.presentation.adapter.ResearchBrandAdapter
import com.doctor.yumyum.presentation.ui.researchlist.ResearchListActivity
import com.doctor.yumyum.presentation.viewmodel.ResearchRecipeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel.mode.observe(viewLifecycleOwner) {
            binding.researchRecipeIbMode.setImageResource(
                if (it == R.string.common_food) R.drawable.ic_change_food else R.drawable.ic_change_beverage
            )
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // 브랜드 아이템 클릭시 이동
        brandRecyclerAdapter = ResearchBrandAdapter {
            val intent = Intent(context, ResearchListActivity::class.java)
            intent.putExtra(getString(R.string.common_brand_en), it)
            startActivity(intent)
        }
        binding.researchRecipeRecyclerviewBrand.adapter = brandRecyclerAdapter
        brandRecyclerAdapter.setBrandList(beverageBrandList)
        brandRecyclerAdapter.notifyDataSetChanged()

        viewModel.mode.observe(viewLifecycleOwner) { mode ->
            changeMode(mode)
        }

        // 주간 랭킹 리스트 조회
        CoroutineScope(Dispatchers.IO).launch {
            // TODO: mode에 따라 파라미터 바꾸기
            coroutineScope { viewModel.getRankRecipe(getString(R.string.common_food), 9, 7) }
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeMode(mode: Int) {
        if (mode == R.string.common_beverage) {
            brandRecyclerAdapter.setBrandList(beverageBrandList)
        } else {
            brandRecyclerAdapter.setBrandList(foodBrandList)
        }
        brandRecyclerAdapter.notifyDataSetChanged()
    }
}