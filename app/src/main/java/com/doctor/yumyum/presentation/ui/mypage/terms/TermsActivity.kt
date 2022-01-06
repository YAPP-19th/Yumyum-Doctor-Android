package com.doctor.yumyum.presentation.ui.mypage.terms

import android.os.Bundle
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityTermsBinding

class TermsActivity : BaseActivity<ActivityTermsBinding>(R.layout.activity_terms) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.apply {
            termsClAppbar.appbarIbBack.setOnClickListener {
                onBackPressed()
            }
            termsBtnOk.setOnClickListener {
                onBackPressed()
            }
        }
    }
}