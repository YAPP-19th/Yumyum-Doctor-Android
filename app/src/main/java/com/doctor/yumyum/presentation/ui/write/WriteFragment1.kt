package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteFirstBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class WriteFragment1 : BaseFragment<FragmentWriteFirstBinding>(R.layout.fragment_write_first) {

    private lateinit var bottomSheetDialog : BottomSheetDialog
    private lateinit var bottomSheetView : View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initDialog()

        binding.writeFirstBtnNext.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_first_write_fragment_to_second_write_fragment)
        }
    }

    private fun initBinding() {
        binding.firstFragment = this
    }

    private fun initDialog() {
        bottomSheetView = layoutInflater.inflate(R.layout.dialog_select_brand, null)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
    }

    fun showBottomSheet(){
        bottomSheetDialog.show()
    }
}