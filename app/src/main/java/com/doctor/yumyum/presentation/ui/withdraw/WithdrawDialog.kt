package com.doctor.yumyum.presentation.ui.withdraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogWithdrawBinding

class WithdrawDialog : BaseDialog<DialogWithdrawBinding>(R.layout.dialog_withdraw) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.dialogWithdrawNo.setOnClickListener { dismiss() }

        return binding.root
    }
}