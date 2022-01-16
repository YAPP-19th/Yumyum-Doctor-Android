package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogWriteCancleBinding


class WriteCancleDialog : BaseDialog<DialogWriteCancleBinding>(R.layout.dialog_write_cancle) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.writeCancleNo.setOnClickListener { dismiss() }
        binding.writeCancleYes.setOnClickListener {
            requireActivity().finish()
        }

        return binding.root
    }
}