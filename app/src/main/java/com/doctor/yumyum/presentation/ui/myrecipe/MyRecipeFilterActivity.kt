package com.doctor.yumyum.presentation.ui.myrecipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels

import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityMyRecipeFilterBinding
import com.doctor.yumyum.databinding.FragmentHomeBindingImpl
import com.doctor.yumyum.presentation.ui.myrecipe.viewmodel.MyRecipeFilterViewModel


class MyRecipeFilterActivity :
    BaseActivity<ActivityMyRecipeFilterBinding>(R.layout.activity_my_recipe_filter) {
    private val filterViewModel: MyRecipeFilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        setFilter()
    }

    private fun setFilter() {
        //TODO : 이미 적용된 값 Setting , 공개비공개여부, 브랜드 값이 background&Text 컬러가 변하지 않음 ㅠ..ㅠ..ㅠ..
        intent.extras?.getString(MyRecipeFragment.STATUS)?.let { filterViewModel.setStatus(it) }
        intent.extras?.getString(MyRecipeFragment.MIN)?.let { filterViewModel.setMinPrice(it) }
        intent.extras?.getString(MyRecipeFragment.MAX)?.let { filterViewModel.setMaxPrice(it) }
        intent.extras?.getString(MyRecipeFragment.CATEGORY)?.let { filterViewModel.setCategory(it) }
        intent.extras?.getStringArrayList(MyRecipeFragment.TASTE)?.let { filterViewModel.setTasteList(it) }
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.activity = this
        binding.viewModel = filterViewModel
        binding.writeToolbar.appbarIbBack.setOnClickListener { finish() }
    }

    fun finishFilter() {
        val mode = filterViewModel.mode.value
        val minPrice = filterViewModel.minPrice.value
        val maxPrice = filterViewModel.maxPrice.value
        val status = filterViewModel.status.value
        val category = filterViewModel.category.value
        val tasteList = filterViewModel.tasteList.value ?: arrayListOf()
        if (!minPrice.isNullOrBlank() && !maxPrice.isNullOrBlank() && minPrice.toInt() > maxPrice.toInt()) {
            // 최소 가격이 최대 가격보다 큰 경우
            showToast(getString(R.string.error_filter_price_range))
        } else {
            val intent = Intent(this, MyRecipeFragment::class.java)
            intent.putExtra(MyRecipeFragment.MODE, mode)
            intent.putExtra(MyRecipeFragment.MIN, minPrice)
            intent.putExtra(MyRecipeFragment.MAX, maxPrice)
            intent.putExtra(MyRecipeFragment.CATEGORY, category)
            intent.putExtra(MyRecipeFragment.STATUS, status)
            intent.putStringArrayListExtra(MyRecipeFragment.TASTE, tasteList)
            setResult(MyRecipeFragment.FILTER_APPLY, intent)
            finish()
        }
    }

    fun resetFilter() {
        val mode = filterViewModel.mode.value
        val intent = Intent(this, MyRecipeFragment::class.java)
        intent.putExtra(MyRecipeFragment.MODE, mode)
        setResult(MyRecipeFragment.FILTER_RESET, intent)
        finish()
    }
}
