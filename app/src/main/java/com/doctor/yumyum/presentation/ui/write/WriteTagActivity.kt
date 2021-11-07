package com.doctor.yumyum.presentation.ui.write

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityWriteTagBinding
import com.doctor.yumyum.presentation.adapter.WriteTagAdapter
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteTagViewModel

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

    }

    private fun initViewModel() {
        tagViewModel = WriteTagViewModel()
    }

    private fun initBinding() {
        binding.tagActivity = this
        binding.tagViewModel =tagViewModel
    }

    private fun initTagRecycler() {
        binding.writeTagRvInput.adapter = WriteTagAdapter()
    }

    private fun getRequestCode() {
        requestCode = intent.extras!!.getInt(resources.getString(R.string.write_tag_type))
        val guideText : String = when(requestCode){
            9001 -> resources.getString(R.string.write_tv_add)
            9002 -> resources.getString(R.string.write_tv_minus)
            else -> ""
        }
        tagViewModel.setGuideText(guideText)
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