package com.doctor.yumyum.presentation.ui.report

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityReportBinding

class ReportActivity : BaseActivity<ActivityReportBinding>(R.layout.activity_report) {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ReportViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.reportToolbar.appbarIbBack.setOnClickListener { finish() }

        viewModel.setRecipeId(intent.getIntExtra("recipeId", 0))

        // 레시피 신고 성공
        viewModel.reportResult.observe(this) {
            showToast(getString(R.string.report_success))
            finish()
        }
        // 레시피 신고 실패
        viewModel.errorState.observe(this) { resId ->
            showToast(getString(resId))
        }

        // 신고 타입에 따른 신고 메시지 설정
        viewModel.reportType.observe(this) { resId ->
            viewModel.setReportMessage(getString(resId))
        }
    }
}