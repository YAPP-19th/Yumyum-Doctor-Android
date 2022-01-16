package com.doctor.yumyum.presentation.ui.mypage.withdraw

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogWithdrawBinding
import com.doctor.yumyum.presentation.ui.splash.SplashActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WithdrawDialog(private val withdrawViewModel: WithdrawViewModel) :
    BaseDialog<DialogWithdrawBinding>(R.layout.dialog_withdraw) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.dialogWithdrawNo.setOnClickListener { dismiss() }
        binding.dialogWithdrawYes.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch { withdrawViewModel.withdraw() }
            startActivity(
                Intent(
                    this.context,
                    SplashActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
        }
        return binding.root
    }
}