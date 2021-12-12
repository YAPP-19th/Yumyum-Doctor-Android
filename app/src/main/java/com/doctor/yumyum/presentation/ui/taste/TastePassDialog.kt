package com.doctor.yumyum.presentation.ui.taste

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogTastePassBinding
import com.doctor.yumyum.presentation.ui.main.MainActivity

class TastePassDialog : BaseDialog<DialogTastePassBinding>(R.layout.dialog_taste_pass) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.tastePassNo.setOnClickListener { dismiss() }
        binding.tastePassYes.setOnClickListener {
            startActivity(
                Intent(
                    this.context,
                    MainActivity::class.java
                )
            )
        }
        return binding.root
    }
}