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

class RecipeMenuDialog(private val recipeId: Int): BaseDialog<DialogRecipeMenuBinding>(R.layout.dialog_recipe_menu) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.recipeMenuTvReport.setOnClickListener {
            val intent = Intent(this.context, ReportActivity::class.java)
            intent.putExtra("recipeId", recipeId)
            startActivity(intent)
            dismiss()
        }
        return binding.root
    }
}