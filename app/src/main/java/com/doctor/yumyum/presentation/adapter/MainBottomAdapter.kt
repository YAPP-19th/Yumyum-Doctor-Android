package com.doctor.yumyum.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.doctor.yumyum.presentation.ui.main.*

class MainBottomAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            1 -> HomeFragment()
            2 -> SearchRecipeFragment()
            3-> WriteRecipeFragment()
            4-> MyRecipeFragment()
            5-> MyPageFragment()
            else -> HomeFragment()
        }
    }
}