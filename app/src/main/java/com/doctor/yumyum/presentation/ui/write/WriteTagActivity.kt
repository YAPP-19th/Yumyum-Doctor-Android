package com.doctor.yumyum.presentation.ui.write

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityWriteTagBinding
import com.doctor.yumyum.presentation.adapter.WriteTagAdapter
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteTagViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

/**
 *  레시피 재료 입력
 *  - 추가된 재료 & 빼는 재료 해쉬태그로 입력하는 화면
 */

class WriteTagActivity : BaseActivity<ActivityWriteTagBinding>(R.layout.activity_write_tag) {

    private lateinit var tagViewModel: WriteTagViewModel
    private var requestCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBinding()
        initTagRecycler()

        getRequestCode()

        binding.writeTagEtInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tagViewModel.setTagItem()
                binding.writeTagEtInput.text.clear()
            }
            false
        }
    }

    private fun initViewModel() {
        tagViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(WriteTagViewModel::class.java)
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.tagActivity = this
        binding.tagViewModel = tagViewModel
        binding.tagAdapter = WriteTagAdapter()
    }

    private fun initTagRecycler() {
        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            binding.writeTagRvInput.layoutManager = it
            binding.writeTagRvInput.adapter = WriteTagAdapter()
        }
    }

    private fun getRequestCode() {
        requestCode = intent.extras!!.getInt(resources.getString(R.string.write_tag_type))
        val guideText : String = when(requestCode){
            9001 -> resources.getString(R.string.write_tv_add)
            9002 -> resources.getString(R.string.write_tv_minus)
            else -> ""
        }
        binding.writeTagEtInput.hint = guideText
        binding.writeTagTvGuide.text = guideText
    }

    fun finishInput() {
        val intent = Intent(this, WriteFragment2::class.java)
        setResult(requestCode, intent)
        finish()
    }

    fun cancleInput(){
        finish()
    }

}