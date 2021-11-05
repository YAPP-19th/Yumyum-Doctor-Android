package com.doctor.yumyum.presentation.ui.write

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityWriteTagBinding

/**
 *  레시피 재료 입력
 *  - 추가된 재료 & 빼는 재료 해쉬태그로 입력하는 화면
 */

class WriteTagActivity : BaseActivity<ActivityWriteTagBinding>(R.layout.activity_write_tag) {

    private var requestCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getRequestCode()
        initBinding()

    }

    private fun initBinding() {
        binding.tagActivity = this
    }

    private fun getRequestCode() {
        requestCode = intent.extras!!.getInt(resources.getString(R.string.write_tag_type))
        Log.d("tagType",requestCode.toString())
    }

    fun finishInput() {
        val intent = Intent(this, WriteFragment2::class.java)
        setResult(requestCode, intent)
        finish()
    }

}