package com.doctor.yumyum.presentation.ui.myrecipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels

import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityMyRecipeFilterBinding
import com.doctor.yumyum.presentation.ui.myrecipe.viewmodel.MyRecipeFilterViewModel


class MyRecipeFilterActivity :
    BaseActivity<ActivityMyRecipeFilterBinding>(R.layout.activity_my_recipe_filter) {
    private val filterViewModel : MyRecipeFilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        finishFilter()
    }

    private fun finishFilter() {
        binding.myFilterBtnSelect.setOnClickListener {
            val mode = filterViewModel.mode.value
            val minPrice = filterViewModel.minPrice.value
            val maxPrice = filterViewModel.maxPrice.value
            val status = filterViewModel.status.value?.name
            val category = filterViewModel.category.value
            val tasteList = filterViewModel.tasteList.value?: arrayListOf()
            Log.d("Filter","$minPrice , $maxPrice , $status , $category , $tasteList")
            if(!minPrice.isNullOrBlank() && !maxPrice.isNullOrBlank() && minPrice.toInt() > maxPrice.toInt()){
                // 최소 가격이 최대 가격보다 큰 경우
                showToast(getString(R.string.error_filter_price_range))
            }else{
                val intent = Intent(this, MyRecipeFragment::class.java)
                intent.putExtra(MyRecipeFragment.MODE,mode)
                intent.putExtra(MyRecipeFragment.MIN,minPrice)
                intent.putExtra(MyRecipeFragment.MAX,maxPrice)
                intent.putExtra(MyRecipeFragment.CATEGORY,category)
                intent.putExtra(MyRecipeFragment.STATUS,status)
                intent.putStringArrayListExtra(MyRecipeFragment.TASTE,tasteList)
                setResult(RESULT_OK,intent)
                finish()
            }
        }
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = filterViewModel
        binding.writeToolbar.appbarIbBack.setOnClickListener { finish() }
    }
}