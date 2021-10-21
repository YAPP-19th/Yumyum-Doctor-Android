package com.doctor.yumyum.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteRecipeBinding
import com.doctor.yumyum.presentation.viewmodel.WriteRecipeViewModel

class WriteRecipeFragment :
    BaseFragment<FragmentWriteRecipeBinding>(R.layout.fragment_write_recipe) {

    private lateinit var viewModel: WriteRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WriteRecipeViewModel::class.java)
        binding.viewModel = viewModel
    }


}