package com.doctor.yumyum.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogErrorBinding


class ErrorDialog :BaseDialog<DialogErrorBinding>(R.layout.dialog_error){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.errorBtn.setOnClickListener {
            val loginIntent = Intent(this.context, LoginActivity::class.java)
            startActivity(loginIntent)
        }
        return binding.root
    }
}
