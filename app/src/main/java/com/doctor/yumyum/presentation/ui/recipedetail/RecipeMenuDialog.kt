package com.doctor.yumyum.presentation.ui.recipedetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogRecipeMenuBinding
import com.doctor.yumyum.presentation.ui.report.ReportActivity

class RecipeMenuDialog: BaseDialog<DialogRecipeMenuBinding>(R.layout.dialog_recipe_menu) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.recipeMenuTvReport.setOnClickListener {
            val intent = Intent(this.context, ReportActivity::class.java)
            //TODO: 신고하기에 필요한 데이터 넘기기 ex) recipe id
            startActivity(intent)
            dismiss()
        }
        return binding.root
    }
}