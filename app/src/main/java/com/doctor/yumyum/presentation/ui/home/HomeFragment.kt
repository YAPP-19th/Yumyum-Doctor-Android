package com.doctor.yumyum.presentation.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.common.utils.REQUEST_CODE
import com.doctor.yumyum.databinding.FragmentHomeBinding
import com.doctor.yumyum.presentation.adapter.HomeBrandAdapter
import com.doctor.yumyum.presentation.adapter.HomeFavoriteAdapter
import com.doctor.yumyum.presentation.adapter.HomeTodayAdapter
import com.doctor.yumyum.presentation.ui.main.MainActivity
import com.doctor.yumyum.presentation.ui.recipedetail.RecipeDetailActivity
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
            Pair(R.drawable.ic_brand_starbucks_home, getString(R.string.common_starbucks)),
            Pair(R.drawable.ic_brand_amasvin_home, getString(R.string.common_amasvin)),
            Pair(R.drawable.ic_brand_gongcha_home, getString(R.string.common_gongcha)),
            Pair(R.drawable.ic_brand_ediya_home, getString(R.string.common_ediya)),
            Pair(R.drawable.ic_brand_cocktail_home, getString(R.string.common_cocktail)),
            Pair(R.drawable.ic_brand_etc_home, getString(R.string.common_etc))
        )
    }
    private val foodBrandList: MutableList<Pair<Int, String>> by lazy {
        mutableListOf(
            Pair(R.drawable.ic_brand_sandwich_home, getString(R.string.common_sandwich)),
            Pair(R.drawable.ic_brand_maratang_home, getString(R.string.common_maratang)),
            Pair(R.drawable.ic_brand_tteokbokki_home, getString(R.string.common_tteokbokki)),
            Pair(R.drawable.ic_brand_salad_home, getString(R.string.common_salad)),
            Pair(R.drawable.ic_brand_yogurt_home, getString(R.string.common_yogurt)),
            Pair(R.drawable.ic_brand_ramen_home, getString(R.string.common_ramen))
        )
    }
    private val detailLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == REQUEST_CODE.DELETE_RECIPE) {
                CoroutineScope(Dispatchers.IO).launch {
                    val mode = viewModel.mode.value ?: R.string.common_food
                    viewModel.getFavorite(requireContext().getString(mode))
                    viewModel.getRecommendation(requireContext().getString(mode))
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeBtnMyRecipe.setOnClickListener {
            val activity = activity as MainActivity
            activity.replaceMyRecipe()
        }

        // 닉네임 초기화
        initNickname()

        viewModel.userInfo.observe(viewLifecycleOwner) {
            if (it) {
                binding.homeTvGreeting.text = getString(
                    R.string.home_tv_greeting,
                    viewModel.nickname.value,
                    viewModel.grade.value
                )
            }
        }

        // 최애 레시피 초기화
        binding.homeRvFavoriteRecipe.adapter = HomeFavoriteAdapter {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra("recipeId", it)
            detailLauncher.launch(intent)
        }

        // 나의 레시피 초기화
        brandRecyclerAdapter = HomeBrandAdapter { brand ->
            val activity = activity as MainActivity
            activity.replaceMyRecipeWithBrand(brand)
        }
        binding.homeRvBrand.adapter = brandRecyclerAdapter

        // 오늘의 추천레시피 초기화
        binding.homeVpTodayRecipe.adapter = HomeTodayAdapter { recipeId ->
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra("recipeId", recipeId)
            startActivity(intent)
        }

        // 음식 <=> 음료
        viewModel.mode.observe(viewLifecycleOwner) {
            binding.homeSwMode.trackTintList = ResourcesCompat.getColorStateList(
                requireContext().resources,
                if (it == R.string.common_food) R.color.main_orange else R.color.sub_green,
                null
            )
            binding.homeSwMode.setThumbResource(if (it == R.string.common_food) R.drawable.sw_mode_thumb_food else R.drawable.sw_mode_thumb_drink)
            changeBrandMode(it)
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getFavorite(requireContext().getString(it))
                viewModel.getRecommendation(requireContext().getString(it))
            }
        }
    }

    private fun initNickname() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getUserInfo()
        }
    }

    private fun changeBrandMode(mode: Int) {
        if (mode == R.string.common_beverage) {
            brandRecyclerAdapter.setBrandList(beverageBrandList)
        } else {
            brandRecyclerAdapter.setBrandList(foodBrandList)
        }
    }
}