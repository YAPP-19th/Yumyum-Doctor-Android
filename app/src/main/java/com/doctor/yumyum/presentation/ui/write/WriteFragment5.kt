package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteFifthBinding
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteViewModel

/**
 * 레시피 작성하기 5
 * - 사진(필수), 텍스트(필수아님)로 리뷰 작성하기
 * - 비공개 여부 결정
 */
class WriteFragment5 : BaseFragment<FragmentWriteFifthBinding>(R.layout.fragment_write_fifth) {
    private val writeViewModel : WriteViewModel by activityViewModels {
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteViewModel() as T
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
    }

    private fun initBinding() {
        binding.viewModel = writeViewModel
    }
}