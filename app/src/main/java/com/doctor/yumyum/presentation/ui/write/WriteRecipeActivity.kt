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

    }

    override fun onClick(v: View?) {
        when(v){
            binding.writeToolbar.appbarIbBack ->
                //TODO :: 첫번째 작성하기에서 뒤로가기를 눌렀을땐 레시피 작성 종료
                findNavController(R.id.nav_host).popBackStack()
            binding.writeToolbar.appbarTvSub ->
                //TODO :: 정말로~~취소하시겠습니까~~? 다이얼로그 띄우기
                finish()
        }
    }
}