package com.doctor.yumyum.presentation.ui.taste

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentTasteDetailBinding
import com.doctor.yumyum.presentation.viewmodel.MyPageViewModel

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
    }
}