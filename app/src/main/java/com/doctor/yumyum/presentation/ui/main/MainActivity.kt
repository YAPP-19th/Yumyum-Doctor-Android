package com.doctor.yumyum.presentation.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.databinding.ActivityMainBinding
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.presentation.ui.home.HomeFragment
import com.doctor.yumyum.presentation.ui.mypage.MyPageFragment
import com.doctor.yumyum.presentation.ui.myrecipe.MyRecipeFragment
import com.doctor.yumyum.presentation.ui.researchrecipe.ResearchRecipeFragment
import com.doctor.yumyum.presentation.ui.write.WriteRecipeActivity




class MainActivity : BaseActivity<ActivityMainBinding>(com.doctor.yumyum.R.layout.activity_main) {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.main_fl_frag, HomeFragment())
            .commit()

        //viewmodel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        window.statusBarColor = Color.WHITE

        //binding
        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = viewModel
            mainNvBottom.menu.getItem(2).isEnabled = false
            mainNvBottom.setOnItemSelectedListener { it ->
                when (it.itemId) {
                    R.id.menu_main_home -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl_frag, HomeFragment()).commit()

                    R.id.menu_main_research_recipe -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl_frag, ResearchRecipeFragment()).commit()

                    R.id.menu_main_my_recipe -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl_frag, MyRecipeFragment()).commit()

                    R.id.menu_main_my_page -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl_frag, MyPageFragment()).commit()

                }
                return@setOnItemSelectedListener true
            }
            mainBtnWrite.setOnClickListener {
                startActivity(Intent(this@MainActivity, WriteRecipeActivity::class.java))
            }
        }
    }

    fun replaceMyRecipe() {
        binding.mainNvBottom.selectedItemId = R.id.menu_main_my_recipe
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fl_frag, MyRecipeFragment()).commit()
    }
}