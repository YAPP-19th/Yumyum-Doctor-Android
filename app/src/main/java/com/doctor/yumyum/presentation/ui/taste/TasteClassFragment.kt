package com.doctor.yumyum.presentation.ui.taste

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentTasteClassBinding

class TasteClassFragment :
    BaseFragment<FragmentTasteClassBinding>(R.layout.fragment_taste_class) {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(TasteViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setMode(0)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}