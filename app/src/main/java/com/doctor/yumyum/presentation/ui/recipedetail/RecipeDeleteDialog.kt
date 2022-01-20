package com.doctor.yumyum.presentation.ui.recipedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogWriteBinding


class RecipeDeleteDialog(private val deleteListener : () -> Unit) : BaseDialog<DialogWriteBinding>(R.layout.dialog_write) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.writeFinishTitle.text = resources.getString(R.string.delete_dialog_tv_title)
        binding.writeFinishNo.setOnClickListener { dismiss() }
        binding.writeFinishYes.setOnClickListener {
            deleteListener()
            dismiss()
        }

        return binding.root
    }
}