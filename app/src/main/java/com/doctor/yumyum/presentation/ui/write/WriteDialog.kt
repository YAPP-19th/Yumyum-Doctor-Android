package com.doctor.yumyum.presentation.ui.write

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogRecipeMenuBinding
import com.doctor.yumyum.databinding.DialogWriteBinding
import com.doctor.yumyum.presentation.ui.report.ReportActivity

class WriteDialog: BaseDialog<DialogWriteBinding>(R.layout.dialog_write) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.writeFinishNo.setOnClickListener { dismiss() }
        
        return binding.root
    }
}