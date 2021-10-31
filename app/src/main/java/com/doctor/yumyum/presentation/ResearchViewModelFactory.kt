package com.doctor.yumyum.presentation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.presentation.viewmodel.ResearchRecipeViewModel

@Suppress("UNCHECKED_CAST")
class ResearchViewModelFactory constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(ResearchRecipeViewModel::class.java))
            ResearchRecipeViewModel(
                sharedPreferences
            ) as T
        else throw IllegalArgumentException()
}