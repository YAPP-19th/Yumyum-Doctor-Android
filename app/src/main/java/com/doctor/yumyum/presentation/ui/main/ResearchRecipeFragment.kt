package com.doctor.yumyum.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentResearchRecipeBinding
import com.doctor.yumyum.presentation.viewmodel.ResearchRecipeViewModel

class ResearchRecipeFragment :
    BaseFragment<FragmentResearchRecipeBinding>(R.layout.fragment_research_recipe) {

    private lateinit var viewModel: ResearchRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ResearchRecipeViewModel::class.java)
        binding.viewModel = viewModel
    }

}