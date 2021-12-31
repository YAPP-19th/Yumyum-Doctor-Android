package com.doctor.yumyum.presentation.ui.searchhashtag

import android.os.Bundle
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivitySearchHashtagBinding

class SearchHashtagActivity :
    BaseActivity<ActivitySearchHashtagBinding>(R.layout.activity_search_hashtag) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.searchHashtagIbBack.setOnClickListener { finish() }
    }
}