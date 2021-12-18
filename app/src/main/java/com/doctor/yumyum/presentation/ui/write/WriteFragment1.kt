package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.data.local.LocalDataSourceImpl
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

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetBinding : DialogSelectBrandBinding
    private lateinit var bottomSheetView: View
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
        writeViewModel.category.observe(viewLifecycleOwner){bottomSheetDialog.dismiss()}
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
        bottomSheetView = layoutInflater.inflate(R.layout.dialog_select_brand, null)
        bottomSheetBinding = DataBindingUtil.inflate<DialogSelectBrandBinding>(
            layoutInflater,
            R.layout.dialog_select_brand,
            bottomSheetView as ViewGroup,
            false
        )

        bottomSheetBinding.apply {
            lifecycleOwner = this@WriteFragment1
            viewModel = writeViewModel
            writeFirst = this@WriteFragment1
        }

        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
    }

    fun showBottomSheet() {
        bottomSheetDialog.show()
    }

    fun onClickBrand(v : View){
        v as TextView
        val tempText = v.text.toString()
        writeViewModel.setTempCategory(tempText)
        writeViewModel.tempCategory.observe(this){
            if(tempText == it){
                v.background = context?.getDrawable(R.drawable.bg_tv_select)
            }else{
                v.background = null
            }
        }
    }
}