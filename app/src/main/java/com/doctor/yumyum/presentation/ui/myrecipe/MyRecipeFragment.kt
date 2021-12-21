package com.doctor.yumyum.presentation.ui.myrecipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentMyRecipeBinding
import com.doctor.yumyum.presentation.ui.write.WriteTagActivity


class MyRecipeFragment : BaseFragment<FragmentMyRecipeBinding>(R.layout.fragment_my_recipe) {
    private lateinit var sortLauncher: ActivityResultLauncher<Intent>
    private val myRecipeViewModel : MyRecipeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        startForSort()
        binding.lifecycleOwner = this
        binding.viewModel = myRecipeViewModel

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
}