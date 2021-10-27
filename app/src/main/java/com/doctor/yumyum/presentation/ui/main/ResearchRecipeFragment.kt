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
        // TODO: sharedpreferences에서 현재 모드 가져오기
        viewModel = ResearchRecipeViewModel("음식")
        viewModel.mode.observe(viewLifecycleOwner) {
            if (it.equals("음식")) {
                binding.researchRecipeIbMode.setImageResource(R.drawable.ic_change_beverage)
            } else {
                binding.researchRecipeIbMode.setImageResource(R.drawable.ic_change_food)
            }
        }
        binding.viewModel = viewModel
    }

}