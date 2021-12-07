package com.doctor.yumyum.presentation.ui.taste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityTasteBinding
import com.doctor.yumyum.presentation.ui.login.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasteActivity : BaseActivity<ActivityTasteBinding>(R.layout.activity_taste) {
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[TasteViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    fun init(){
        //binding
        binding.apply {
            lifecycleOwner = this@TasteActivity
            viewModel = viewModel
            tasteToolbar.appbarTvSub.apply {
                visibility = View.VISIBLE
                text = getString(R.string.common_next)
            }
            tasteToolbar.appbarIbBack.setOnClickListener {
                onBackPressed()
            }
            tasteBtnNext.setOnClickListener {
                Navigation.findNavController(tasteNav).navigate(R.id.action_tasteClassFragment_to_tasteDetailFragment)
            }
        }
    }
}