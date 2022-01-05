package com.doctor.yumyum.presentation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseDialog
import com.doctor.yumyum.databinding.DialogGradeUpBinding

class GradeUpDialog(private val grade: String) :
    BaseDialog<DialogGradeUpBinding>(R.layout.dialog_grade_up) {

    private val gradeBadge: Map<String, Int> by lazy {
        mapOf(
            getString(R.string.grade_bachelor) to R.drawable.ic_grade_up_bachelor,
            getString(R.string.grade_master) to R.drawable.ic_grade_up_master,
            getString(R.string.grade_expert) to R.drawable.ic_grade_up_expert,
            getString(R.string.grade_professor) to R.drawable.ic_grade_up_professor
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.dialogGradeUpTvContent.text = getString(R.string.dialog_grade_up_content, grade)
        gradeBadge[grade]?.let { binding.dialogGradeUpIvBadge.setImageResource(it) }
        binding.dialogGradeUpBtnOk.setOnClickListener { dismiss() }
        return binding.root
    }
}