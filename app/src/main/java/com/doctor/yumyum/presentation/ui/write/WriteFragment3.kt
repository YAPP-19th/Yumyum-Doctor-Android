package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteThirdBinding
import com.doctor.yumyum.presentation.adapter.WriteTagAdapter
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteViewModel

/**
 *  레시피 작성하기 3
 *  - 작성한 메인메뉴와 재료들을 보여주기
 *  - 가격 입력하기
 */

class WriteFragment3 : BaseFragment<FragmentWriteThirdBinding>(R.layout.fragment_write_third) {
    private val writeViewModel: WriteViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteViewModel() as T
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()

        binding.writeThirdBtnNext.setOnClickListener {
            findNavController().navigate(R.id.action_third_write_fragment_to_fourth_write_fragment)
        }
        writeViewModel.price.observe(viewLifecycleOwner){
            if (!it.isNullOrBlank()){
                binding.writeThirdBtnNext.isEnabled = true
                binding.writeThirdBtnNext.background = resources.getDrawable(R.drawable.bg_btn_main)
            }else{
                binding.writeThirdBtnNext.isEnabled = false
                binding.writeThirdBtnNext.background = resources.getDrawable(R.drawable.bg_btn_sub)
            }
        }

        writeViewModel.mainIngredient.observe(viewLifecycleOwner){
            binding.writeThirdTvMain.text =resources.getString(R.string.common_tagItem,it)
        }
    }

    private fun initBinding() {
        binding.viewModel = writeViewModel
        binding.writeThirdRvAdd.adapter = WriteTagAdapter {}
        binding.writeThirdRvMinus.adapter = WriteTagAdapter {}
    }
}