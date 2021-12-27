package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.DialogSelectBrandBinding
import com.doctor.yumyum.databinding.FragmentWriteFirstBinding
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 *  레시피 작성하기 1
 *  - 음식 or 음료 선택하기
 *  - 브랜드 선택하기
 */

class WriteFragment1 : BaseFragment<FragmentWriteFirstBinding>(R.layout.fragment_write_first) {

    private lateinit var brandSelectDialog: BottomSheetDialog
    private lateinit var brandSelectBinding: DialogSelectBrandBinding
    private lateinit var brandSelectView: View
    private val writeViewModel: WriteViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteViewModel() as T
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initDialog()
        initListener()

        writeViewModel.initCategory()
        writeViewModel.category.observe(viewLifecycleOwner) { brandSelectDialog.dismiss() }
        writeViewModel.mode.observe(viewLifecycleOwner){
            if(it){
                binding.writeFirstTvSelectBrand.text = resources.getString(R.string.common_starbucks)
            }else{
                binding.writeFirstTvSelectBrand.text = resources.getString(R.string.common_sandwich)
            }

        }
    }

    private fun initListener() {
        binding.writeFirstBtnNext.setOnClickListener {
            findNavController().navigate(R.id.action_first_write_fragment_to_second_write_fragment)
        }
    }

    private fun initBinding() {
        binding.firstFragment = this
        binding.viewModel = writeViewModel
    }

    private fun initDialog() {
        brandSelectView = layoutInflater.inflate(R.layout.dialog_select_brand, null)
        brandSelectBinding = DataBindingUtil.inflate<DialogSelectBrandBinding>(
            layoutInflater,
            R.layout.dialog_select_brand,
            brandSelectView as ViewGroup,
            false
        )

        brandSelectBinding.apply {
            lifecycleOwner = this@WriteFragment1
            viewModel = writeViewModel
            writeFirst = this@WriteFragment1
        }

        brandSelectDialog = BottomSheetDialog(requireContext())
        brandSelectDialog.setContentView(brandSelectBinding.root)
    }

    fun showBottomSheet() {
        brandSelectDialog.show()
    }
}