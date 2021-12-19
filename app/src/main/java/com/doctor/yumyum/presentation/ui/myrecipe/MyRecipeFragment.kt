package com.doctor.yumyum.presentation.ui.myrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentMyRecipeBinding


class MyRecipeFragment : BaseFragment<FragmentMyRecipeBinding>(R.layout.fragment_my_recipe) {

    private  lateinit var myRecipeViewModel : MyRecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myRecipeViewModel = ViewModelProvider(this).get(MyRecipeViewModel::class.java)
        binding.viewModel = myRecipeViewModel
    }
}