package com.doctor.yumyum.presentation.ui.filter

import android.os.Bundle
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityFilterBinding

class FilterActivity : BaseActivity<ActivityFilterBinding>(R.layout.activity_filter) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}