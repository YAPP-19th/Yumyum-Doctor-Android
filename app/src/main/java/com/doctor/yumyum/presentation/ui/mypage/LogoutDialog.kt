package com.doctor.yumyum.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogLogoutBinding
import com.doctor.yumyum.presentation.ui.splash.SplashActivity

class LogoutDialog(private val myPageViewModel: MyPageViewModel?) :
    BaseDialog<DialogLogoutBinding>(R.layout.dialog_logout) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.dialogLogoutNo.setOnClickListener { dismiss() }
        binding.dialogLogoutYes.setOnClickListener {
            myPageViewModel?.logout()
            startActivity(Intent(this.context, SplashActivity::class.java))
        }
        return binding.root
    }
}