package com.doctor.yumyum.presentation.ui.myrecipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.DialogMyRecipeSortBinding
import com.doctor.yumyum.databinding.DialogSelectBrandBinding
import com.doctor.yumyum.databinding.DialogSelectSortBinding
import com.doctor.yumyum.databinding.FragmentMyRecipeBinding
import com.doctor.yumyum.presentation.ui.write.WriteTagActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class MyRecipeFragment : BaseFragment<FragmentMyRecipeBinding>(R.layout.fragment_my_recipe) {

    private lateinit var sortLauncher: ActivityResultLauncher<Intent>
    private val myRecipeViewModel : MyRecipeViewModel by viewModels()
    private lateinit var sortSelectDialog: BottomSheetDialog
    private lateinit var sortSelectBinding: DialogMyRecipeSortBinding
    private lateinit var sortSelectView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDialog()
        startForSort()

        binding.lifecycleOwner = this
        binding.viewModel = myRecipeViewModel
        binding.myRecipeFragment = this

        myRecipeViewModel.mode.observe(viewLifecycleOwner) {
            binding.myRecipeIbMode.setImageResource(
                if (it == R.string.common_food) R.drawable.ic_change_food else R.drawable.ic_change_beverage
            )
        }
    }

    private fun startForSort() {
        sortLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //TODO : 정렬 정보 가져오기
        }
        binding.myRecipeIbSort.setOnClickListener {
            val intent = Intent(context, MyPageFilterActivity::class.java)
            sortLauncher.launch(intent)
        }
    }

    private fun initDialog() {
        sortSelectView = layoutInflater.inflate(R.layout.dialog_my_recipe_sort, null)
        sortSelectBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_my_recipe_sort,
            sortSelectView as ViewGroup,
            false
        )

        sortSelectBinding.apply {
            lifecycleOwner = this@MyRecipeFragment
        }

        sortSelectDialog = BottomSheetDialog(requireContext())
        sortSelectDialog.setContentView(sortSelectBinding.root)
    }

    fun showBottomSheet() {
        sortSelectDialog.show()
    }
}