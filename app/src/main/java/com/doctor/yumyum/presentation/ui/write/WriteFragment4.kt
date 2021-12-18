package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteFourthBinding

/**
 * 레시피 작성하기 4
 * - 맛과 세부맛 선택하기
 */
class WriteFragment4 : BaseFragment<FragmentWriteFourthBinding>(R.layout.fragment_write_fourth) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.writeFourthBtnNext.setOnClickListener {
            findNavController().navigate(R.id.action_fourth_write_fragment_to_fifth_write_fragment)
        }
    }

}