package com.doctor.yumyum.presentation.ui.withdraw

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityWithdrawBinding

class WithdrawActivity : BaseActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[WithdrawViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.withdrawAppbar.appbarIbBack.setOnClickListener {
            onBackPressed()
        }
        binding.withdrawTvWithdraw.setOnClickListener {
            WithdrawDialog().apply {
                show(supportFragmentManager, "TastePassDialog")
            }
        }
    }
}