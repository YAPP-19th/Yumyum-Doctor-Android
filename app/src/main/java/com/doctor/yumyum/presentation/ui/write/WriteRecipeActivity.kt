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

        binding.writeToolbar.appbarIbBack.setOnClickListener(this)
        binding.writeToolbar.appbarTvSub.setOnClickListener(this)
        binding.writeToolbar.appbarTvSub.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {
        when(v){
            binding.writeToolbar.appbarIbBack ->
                onBackPressed()
            binding.writeToolbar.appbarTvSub ->
                WriteCancleDialog().apply {
                    show(supportFragmentManager,"WriteCancleDialog")
                }
        }
    }

    override fun onBackPressed() {
        val result = findNavController(R.id.nav_host).popBackStack()
        if(!result) finish()
    }
}