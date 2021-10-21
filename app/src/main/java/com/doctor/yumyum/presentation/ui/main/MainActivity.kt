package com.doctor.yumyum.presentation.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.databinding.ActivityMainBinding
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.presentation.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

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

        //binding
        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = viewModel
            mainNvBottom.setOnItemSelectedListener { it ->
                when (it.itemId) {
                    R.id.menu_main_home -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl_frag, HomeFragment()).commit()

                    R.id.menu_main_search_recipe -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl_frag, SearchRecipeFragment()).commit()

                    R.id.menu_main_write_recipe -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl_frag, WriteRecipeFragment()).commit()

                    R.id.menu_main_my_recipe -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl_frag, MyRecipeFragment()).commit()

                    R.id.menu_main_my_page -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl_frag, MyPageFragment()).commit()

                    else -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl_frag, HomeFragment()).commit()

                }
                return@setOnItemSelectedListener true
            }
        }

    }
}