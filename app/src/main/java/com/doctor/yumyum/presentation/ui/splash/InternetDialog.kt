package com.doctor.yumyum.presentation.ui.splash

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogInternetBinding
import com.doctor.yumyum.presentation.ui.main.MainActivity

class InternetDialog : BaseDialog<DialogInternetBinding>(R.layout.dialog_internet) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.internetBtn.setOnClickListener {
            val splashIntent = Intent(this.context, SplashActivity::class.java)
            startActivity(splashIntent)
        }
        return binding.root
    }
}