package com.doctor.yumyum.presentation.ui.main

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentResearchRecipeBinding
import com.doctor.yumyum.presentation.ResearchViewModelFactory
import com.doctor.yumyum.presentation.viewmodel.ResearchRecipeViewModel

class ResearchRecipeFragment :
    BaseFragment<FragmentResearchRecipeBinding>(R.layout.fragment_research_recipe) {

    // TODO: sharedpreferences object 불러오기
    private val sharedPreferences by lazy {
        context?.getSharedPreferences(
            getString(R.string.shared_pref_key),
            Application.MODE_PRIVATE
        )
    }
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ResearchViewModelFactory(sharedPreferences!!)
        )[ResearchRecipeViewModel::class.java]
    }

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
        return binding.root
    }
}