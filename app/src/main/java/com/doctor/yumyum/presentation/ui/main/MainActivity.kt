package com.doctor.yumyum.presentation.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.databinding.ActivityMainBinding
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.presentation.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.main_ll_frag, HomeFragment())
            .commit()

        //viewmodel
        vm = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        //binding
        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = vm
            mainNvBottom.setOnItemSelectedListener { it ->
                when (it.itemId) {
                    R.id.item_main_bottom_home -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_ll_frag, HomeFragment()).commit()

                    R.id.item_main_bottom_search_recipe -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_ll_frag, SearchRecipeFragment()).commit()

                    R.id.item_main_bottom_write_recipe -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_ll_frag, WriteRecipeFragment()).commit()

                    R.id.item_main_bottom_my_recipe -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_ll_frag, MyRecipeFragment()).commit()

                    R.id.item_main_bottom_my_page -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_ll_frag, MyPageFragment()).commit()

                    else -> supportFragmentManager.beginTransaction()
                        .replace(R.id.main_ll_frag, HomeFragment()).commit()

                }
                return@setOnItemSelectedListener true
            }
        }

    }
}