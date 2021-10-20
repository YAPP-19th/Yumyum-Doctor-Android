package com.doctor.yumyum.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.doctor.yumyum.presentation.ui.main.*

class MainViewpagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> SearchRecipeFragment()
            2-> WriteRecipeFragment()
            3-> MyRecipeFragment()
            4-> MyPageFragment()
            else -> HomeFragment()
        }
    }
}