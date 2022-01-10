package com.doctor.yumyum.presentation.ui.nickname

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogNicknameBackBinding
import com.doctor.yumyum.presentation.ui.main.MainActivity

class NicknameBackDialog :BaseDialog<DialogNicknameBackBinding>(R.layout.dialog_nickname_back){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.dialogNicknameBackNo.setOnClickListener { dismiss() }
        binding.dialogNicknameBackYes.setOnClickListener {
            startActivity(
            Intent(
                this.context,
                MainActivity::class.java
            )
        ) }

        return binding.root
    }

}