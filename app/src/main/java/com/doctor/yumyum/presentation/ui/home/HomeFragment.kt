package com.doctor.yumyum.presentation.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentHomeBinding
import com.doctor.yumyum.presentation.adapter.HomeBrandAdapter
import com.doctor.yumyum.presentation.ui.main.MainActivity
import com.doctor.yumyum.presentation.ui.myrecipe.MyRecipeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]
    }
    private lateinit var brandRecyclerAdapter: HomeBrandAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
            homeBtnMyRecipe.setOnClickListener {
                val activity = activity as MainActivity
                activity.replaceMyRecipe()
            }
        }

        // 닉네임 초기화
        initNickname()
        viewModel.nickname.observe(viewLifecycleOwner) {
            binding.homeTvGreeting.text = getString(R.string.home_tv_greeting, it)
        }

        brandRecyclerAdapter = HomeBrandAdapter { brand ->
            val activity = activity as MainActivity
            activity.replaceMyRecipeWithBrand(brand)
        }
        binding.homeRvBrand.adapter = brandRecyclerAdapter


        viewModel.mode.observe(viewLifecycleOwner) {
            binding.homeIbMode.setImageResource(
                if (it == R.string.common_food) R.drawable.ic_change_food else R.drawable.ic_change_beverage
            )
            changeBrandMode(it)
        }
    }

    private fun initNickname() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getUserNickname()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeBrandMode(mode: Int) {
        if (mode == R.string.common_beverage) {
            brandRecyclerAdapter.setBrandList(beverageBrandList)
        } else {
            brandRecyclerAdapter.setBrandList(foodBrandList)
        }
        brandRecyclerAdapter.notifyDataSetChanged()
    }
}