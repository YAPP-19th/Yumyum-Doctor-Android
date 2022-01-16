package com.doctor.yumyum.presentation.ui.mypage.myinfo

import android.content.Intent
import android.os.Bundle
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityMyInfoBinding
import com.doctor.yumyum.presentation.ui.nickname.NicknameActivity
import com.doctor.yumyum.presentation.ui.mypage.withdraw.WithdrawActivity

class MyInfoActivity : BaseActivity<ActivityMyInfoBinding>(R.layout.activity_my_info) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.apply {
            myInfoAppbar.appbarIbBack.setOnClickListener {
                onBackPressed()
            }
            myInfoTvNicknameSetting.setOnClickListener {
                val tasteIntent = Intent(this@MyInfoActivity, NicknameActivity::class.java)
                tasteIntent.putExtra(getString(R.string.nickname_mode), true)
                startActivity(tasteIntent)
            }
            myInfoTvWithdraw.setOnClickListener {
                startActivity(Intent(this@MyInfoActivity, WithdrawActivity::class.java))
            }
        }
    }
}