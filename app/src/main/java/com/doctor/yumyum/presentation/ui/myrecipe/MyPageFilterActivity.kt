package com.doctor.yumyum.presentation.ui.myrecipe

import android.os.Bundle
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityMyRecipeFilterBinding

class MyPageFilterActivity :
    BaseActivity<ActivityMyRecipeFilterBinding>(R.layout.activity_my_recipe_filter) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.fragment = this
        binding.writeToolbar.appbarIbBack.setOnClickListener { finish() }
    }
}