package com.doctor.yumyum.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogInternetBinding
import com.doctor.yumyum.presentation.ui.login.LoginActivity

class InternetDialog : BaseDialog<DialogInternetBinding>(R.layout.dialog_internet) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.internetBtn.setOnClickListener {
            val loginIntent = Intent(this.context, LoginActivity::class.java)
            startActivity(loginIntent)
        }
        return binding.root
    }
}