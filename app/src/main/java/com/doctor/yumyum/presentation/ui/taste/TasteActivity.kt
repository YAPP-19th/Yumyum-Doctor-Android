package com.doctor.yumyum.presentation.ui.taste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityTasteBinding
import com.doctor.yumyum.presentation.ui.login.ErrorDialog
import com.doctor.yumyum.presentation.ui.login.LoginViewModel
import com.doctor.yumyum.presentation.ui.main.MainActivity
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

        viewModel.mode.observe(this) { mode ->
            if (mode == 0) setStateClass()
            else setStateDetail()
        }
    }

    fun init() {
        binding.apply {
            lifecycleOwner = this@TasteActivity
            viewModel = viewModel
            tasteToolbar.appbarTvSub.apply {
                visibility = View.VISIBLE
                text = getString(R.string.common_next)
                setOnClickListener {
                    TastePassDialog().apply {
                        show(supportFragmentManager, "TastePassDialog")
                    }
                }
            }
            tasteToolbar.appbarIbBack.setOnClickListener {
                onBackPressed()
            }
            tasteBtnNext.setOnClickListener {
                Navigation.findNavController(tasteNav)
                    .navigate(R.id.action_tasteClassFragment_to_tasteDetailFragment)
            }
        }
    }

    fun setStateClass () {
        binding.tasteTvSubtitle.text = getString(R.string.taste_tv_class)
        binding.tasteBtnNext.text = getString(R.string.common_next)
        binding.tasteBtnNext.setOnClickListener {
            Navigation.findNavController(binding.tasteNav)
                .navigate(R.id.action_tasteClassFragment_to_tasteDetailFragment)
        }
    }
    fun setStateDetail () {
        binding.tasteTvSubtitle.text = getString(R.string.taste_tv_detail)
        binding.tasteBtnNext.text = getString(R.string.common_complete)
        binding.tasteBtnNext.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}