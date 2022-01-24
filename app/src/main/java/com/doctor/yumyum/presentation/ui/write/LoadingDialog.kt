package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogLoadingBinding

class LoadingDialog : BaseDialog<DialogLoadingBinding>(R.layout.dialog_loading) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        isCancelable = false
        return binding.root
    }
}