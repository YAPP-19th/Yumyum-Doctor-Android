package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteFourthBinding
import com.doctor.yumyum.presentation.adapter.DetailTasteAdapter
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteViewModel


/**
 * 레시피 작성하기 4
 * - 맛과 세부맛 선택하기
 */
class WriteFragment4 : BaseFragment<FragmentWriteFourthBinding>(R.layout.fragment_write_fourth) {
    private val writeViewModel: WriteViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteViewModel() as T
        }
    }

    private val detailTasteList: MutableList<Pair<Int, String>> by lazy {
        mutableListOf(
            Pair(R.drawable.ic_taste_detail_sweet, getString(R.string.taste_detail_sweet)),
            Pair(R.drawable.ic_taste_detail_light,getString(R.string.taste_detail_light)),
            Pair(R.drawable.ic_taste_detail_sour,getString(R.string.taste_detail_sour)),
            Pair(R.drawable.ic_taste_detail_cool,getString(R.string.taste_detail_cool)),
            Pair(R.drawable.ic_taste_detail_greasy,getString(R.string.taste_detail_greasy)),
            Pair(R.drawable.ic_taste_detail_hot,getString(R.string.taste_detail_hot)),
            Pair(R.drawable.ic_taste_detail_spicy,getString(R.string.taste_detail_spicy)),
            Pair(R.drawable.ic_taste_detail_neat, getString(R.string.taste_detail_neat)),
            Pair(R.drawable.ic_taste_detail_refresh,getString(R.string.taste_detail_refresh)),
            Pair(R.drawable.ic_taste_detail_salty, getString(R.string.taste_detail_salty)),
            Pair(R.drawable.ic_taste_detail_attractive,getString(R.string.taste_detail_attractive)),
            Pair(R.drawable.ic_taste_detail_savory,getString(R.string.taste_detail_savory))
        )
    }

    private lateinit var detailTasteAdapter : DetailTasteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initRecycler()

        binding.viewModel = writeViewModel
        binding.writeFourthBtnNext.setOnClickListener {
            findNavController().navigate(R.id.action_fourth_write_fragment_to_fifth_write_fragment)
        }

        writeViewModel.tasteList.observe(requireActivity()){
            detailTasteAdapter.updateSelectedList(it)
            if(it.isNullOrEmpty()){
                binding.writeFourthBtnNext.isEnabled = false
                binding.writeFourthBtnNext.background = activity?.getDrawable(R.drawable.bg_btn_sub)
            }else{
                binding.writeFourthBtnNext.isEnabled = true
                binding.writeFourthBtnNext.background = activity?.getDrawable(R.drawable.bg_btn_main)
            }
        }
    }

    private fun initRecycler() {
        detailTasteAdapter = DetailTasteAdapter {
            writeViewModel.updateTasteList(it)
        }
        binding.writeFourthRvDetailTaste.adapter = detailTasteAdapter
        detailTasteAdapter.setTasteList(detailTasteList)
    }

}