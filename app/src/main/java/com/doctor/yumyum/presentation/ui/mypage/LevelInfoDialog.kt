package com.doctor.yumyum.presentation.ui.mypage

import android.os.Bundle
import android.view.*
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogLevelInfoBinding

class LevelInfoDialog : BaseDialog<DialogLevelInfoBinding>(R.layout.dialog_level_info) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

}