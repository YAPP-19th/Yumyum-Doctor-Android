package com.doctor.yumyum.presentation.ui.myrecipe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityMyRecipeFilterBinding
import com.doctor.yumyum.presentation.ui.myrecipe.viewmodel.MyRecipeFilterViewModel
import com.doctor.yumyum.presentation.ui.myrecipe.viewmodel.MyRecipeViewModel

class MyRecipeFilterActivity :
    BaseActivity<ActivityMyRecipeFilterBinding>(R.layout.activity_my_recipe_filter) {
    private val filterViewModel : MyRecipeFilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.fragment = this
        binding.viewModel = filterViewModel
        binding.writeToolbar.appbarIbBack.setOnClickListener { finish() }
    }
}