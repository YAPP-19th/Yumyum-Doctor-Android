package com.doctor.yumyum.presentation.ui.taste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentTasteDetailBinding

class TasteDetailFragment :
    BaseFragment<FragmentTasteDetailBinding>(R.layout.fragment_taste_detail) {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(TasteViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}