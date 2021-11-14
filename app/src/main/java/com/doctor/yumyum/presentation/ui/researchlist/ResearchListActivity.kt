package com.doctor.yumyum.presentation.ui.researchlist

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityResearchListBinding
import com.doctor.yumyum.databinding.DialogSelectSortBinding
import com.doctor.yumyum.presentation.ui.filter.FilterActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class ResearchListActivity :
    BaseActivity<ActivityResearchListBinding>(R.layout.activity_research_list) {

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ResearchListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDialog()

        // 필터 화면으로 이동
        binding.researchListTvFilter.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    FilterActivity::class.java
                )
            )
        }
        // 정렬 다이얼로그 띄우기
        binding.researchListTvSort.setOnClickListener { showBottomSheet() }
        viewModel.sortType.observe(this) { bottomSheetDialog.dismiss() }
    }

    private fun initDialog() {
        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_select_sort, null)
        val bottomSheetBinding = DataBindingUtil.inflate<DialogSelectSortBinding>(
            layoutInflater,
            R.layout.dialog_select_sort,
            bottomSheetView as ViewGroup,
            false
        )
        bottomSheetBinding.lifecycleOwner = this
        bottomSheetBinding.viewModel = viewModel
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
    }

    private fun showBottomSheet() {
        bottomSheetDialog.show()
        viewModel.initSortType()
    }
}