package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogWriteBinding

class WriteDialog(private val finishListener : () -> Unit) : BaseDialog<DialogWriteBinding>(R.layout.dialog_write) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.writeFinishNo.setOnClickListener { dismiss() }
        binding.writeFinishYes.setOnClickListener {
            finishListener()
        }

        return binding.root
    }
}