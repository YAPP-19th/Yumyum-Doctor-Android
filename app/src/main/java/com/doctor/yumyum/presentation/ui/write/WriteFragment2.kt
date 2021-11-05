package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteSecondBinding

/**
 *  레시피 작성하기 2
 *  - 레시피 이름, 메인메뉴 입력하기
 *  - 추가하는 재료 & 빼는 재료 보여주기
 */
class WriteFragment2 : BaseFragment<FragmentWriteSecondBinding>(R.layout.fragment_write_second), View.OnClickListener{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()

        binding.writeSecondBtnMinus.setOnClickListener(this)
        binding.writeSecondBtnNext.setOnClickListener(this)
        binding.writeSecondBtnAdd.setOnClickListener(this)
    }

    private fun initBinding() {
        binding.secondFragment = this
    }

    override fun onClick(v: View?) {
        when(v){
            binding.writeSecondBtnAdd ->
                findNavController().navigate(R.id.action_second_write_fragment_to_tag_write_fragment)

            binding.writeSecondBtnNext->
                findNavController().navigate(R.id.action_second_write_fragment_to_third_write_fragment)

            binding.writeSecondBtnMinus->
                findNavController().navigate(R.id.action_second_write_fragment_to_tag_write_fragment)

        }
    }
}