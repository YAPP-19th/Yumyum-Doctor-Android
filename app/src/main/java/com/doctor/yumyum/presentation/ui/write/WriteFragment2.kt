package com.doctor.yumyum.presentation.ui.write

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteSecondBinding
import com.doctor.yumyum.presentation.adapter.WriteTagAdapter
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteViewModel

/**
 *  레시피 작성하기 2
 *  - 레시피 이름, 메인메뉴 입력하기
 *  - 추가하는 재료 & 빼는 재료 보여주기
 */

class WriteFragment2 : BaseFragment<FragmentWriteSecondBinding>(R.layout.fragment_write_second), View.OnClickListener{
    private lateinit var changeIngredients : ActivityResultLauncher<Intent>
    private var addList : ArrayList<String> = arrayListOf()
    private var minusList : ArrayList<String> = arrayListOf()

    companion object {
        const val REQUEST_CODE_ADD_INGREDIENTS = 9001
        const val REQUEST_CODE_MINUS_INGREDIENTS = 9002
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initListener()
        changeIngredients()
    }

    private fun initBinding() {
        binding.secondFragment = this
        //binding.writeViewModel = writeViewModel
        binding.writeSecondRvPlus.adapter = WriteTagAdapter{}
        binding.writeSecondRvMinus.adapter = WriteTagAdapter{}
    }

    private fun initListener() {
        binding.writeSecondBtnMinus.setOnClickListener(this)
        binding.writeSecondBtnNext.setOnClickListener(this)
        binding.writeSecondBtnAdd.setOnClickListener(this)
        binding.writeSecondIbAddEdit.setOnClickListener(this)
        binding.writeSecondIbMinusEdit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent(context,WriteTagActivity::class.java)
        when(v){
            binding.writeSecondBtnAdd -> {
                intent.putExtra(resources.getString(R.string.write_tag_type), REQUEST_CODE_ADD_INGREDIENTS)
                changeIngredients.launch(intent)
            }
            binding.writeSecondBtnMinus -> {
                intent.putExtra(resources.getString(R.string.write_tag_type), REQUEST_CODE_MINUS_INGREDIENTS)
                changeIngredients.launch(intent)
            }
            binding.writeSecondIbAddEdit -> {
                intent.putExtra(resources.getString(R.string.write_tag_type), REQUEST_CODE_ADD_INGREDIENTS)
                intent.putStringArrayListExtra(resources.getString(R.string.write_intent_inputList), addList)
                changeIngredients.launch(intent)
            }
            binding.writeSecondIbMinusEdit -> {
                intent.putExtra(resources.getString(R.string.write_tag_type), REQUEST_CODE_MINUS_INGREDIENTS)
                intent.putStringArrayListExtra(resources.getString(R.string.write_intent_inputList), addList)
                changeIngredients.launch(intent)
            }

            binding.writeSecondBtnNext->
                findNavController().navigate(R.id.action_second_write_fragment_to_third_write_fragment)

        }
    }

    //TagActivity가 종료되었을 때
    private fun changeIngredients() {
        changeIngredients = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ it ->
            if (it.resultCode == REQUEST_CODE_ADD_INGREDIENTS) {
                addList = it.data?.getStringArrayListExtra(resources.getString(R.string.write_intent_inputList)) as ArrayList<String>
                //writeViewModel.setAddTagItem(addList)
            }

            if(it.resultCode == REQUEST_CODE_MINUS_INGREDIENTS){
                minusList = it.data?.getStringArrayListExtra(resources.getString(R.string.write_intent_inputList)) as ArrayList<String>
                //writeViewModel.setMinusTagItem(minusList)
            }
        }
    }
}