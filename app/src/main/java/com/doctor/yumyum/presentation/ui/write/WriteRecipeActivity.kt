package com.doctor.yumyum.presentation.ui.write

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityWriteRecipeBinding

class WriteRecipeActivity : BaseActivity<ActivityWriteRecipeBinding>(R.layout.activity_write_recipe), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.writeIbBack.setOnClickListener(this)
        binding.writeTvCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.writeIbBack ->
                //TODO :: 첫번째 작성하기에서는 뒤로가기 안보이게 하기
                findNavController(R.id.nav_host).popBackStack()
            binding.writeTvCancel ->
                //TODO :: 다이얼로그 띄우기
                finish()
        }
    }

}