package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteThirdBinding

/**
 *  레시피 작성하기 3
 *  - 작성한 메인메뉴와 재료들을 보여주기
 *  - 가격 입력하기
 */

class WriteFragment3 : BaseFragment<FragmentWriteThirdBinding>(R.layout.fragment_write_third){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.writeThirdBtnNext.setOnClickListener{
            findNavController().navigate(R.id.action_third_write_fragment_to_fourth_write_fragment)
        }
    }
}