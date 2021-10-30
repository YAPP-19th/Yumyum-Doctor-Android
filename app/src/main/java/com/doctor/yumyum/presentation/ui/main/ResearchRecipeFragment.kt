package com.doctor.yumyum.presentation.ui.main

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // TODO: sharedpreferences object 불러오기
        val sharedPreferences =
            context?.getSharedPreferences(
                getString(R.string.shared_pref_key),
                Application.MODE_PRIVATE
            )
        viewModel = ResearchRecipeViewModel(sharedPreferences!!)

        viewModel.mode.observe(viewLifecycleOwner) {
            binding.researchRecipeIbMode.setImageResource(
                if (it == R.string.food) R.string.food else R.drawable.ic_change_food
            )
        }
        binding.viewModel = viewModel
    }

}