package com.doctor.yumyum.presentation.ui.taste

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityTasteBinding
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
            if (mode == 0) {
                if (viewModel.tasteClassState.value?.size == 0 ) setButtonUnavailable()
                else setButtonAvailable()
                setStateClass()
            }
            else {
                if (viewModel.tasteDetailState.value?.size == 0 ) setButtonUnavailable()
                else setButtonAvailable()
                setStateDetail()
            }
        }

        viewModel.tasteClassState.observe(this) { tasteClassState ->
            if (viewModel.mode.value == 0) {
                if (tasteClassState.size == 0) setButtonUnavailable()
                else setButtonAvailable()
            }
        }

        viewModel.tasteDetailState.observe(this) { tasteDetailState ->
            if (viewModel.mode.value == 1) {
                if (tasteDetailState.size == 0) setButtonUnavailable()
                else setButtonAvailable()
            }
        }


        viewModel.tasteClassState.observe(this) { tasteClassState ->
            if (viewModel.mode.value == 0) {
                if (tasteClassState.size == 0) setButtonUnavailable()
                else setButtonAvailable()
            }
        }

        viewModel.tasteDetailState.observe(this) { tasteDetailState ->
            if (viewModel.mode.value == 1) {
                if (tasteDetailState.size == 0) setButtonUnavailable()
                else setButtonAvailable()
            }
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
            tasteToolbar.appbarIbBack.setOnClickListener {
                onBackPressed()
            }
            tasteBtnNext.setOnClickListener {
                Navigation.findNavController(tasteNav)
                    .navigate(R.id.action_tasteClassFragment_to_tasteDetailFragment)
            }
        }
    }

    private fun setStateClass() {
        if (viewModel.tasteClassState.value?.size == 0) setButtonUnavailable()
        binding.tasteTvSubtitle.text = getString(R.string.taste_tv_class)
        binding.tasteBtnNext.text = getString(R.string.common_next)
        binding.tasteBtnNext.setOnClickListener {
            Navigation.findNavController(binding.tasteNav)
                .navigate(R.id.action_tasteClassFragment_to_tasteDetailFragment)
        }
    }

    private fun setStateDetail() {
        if (viewModel.tasteDetailState.value?.size == 0) setButtonUnavailable()
        binding.tasteTvSubtitle.text = getString(R.string.taste_tv_detail)
        binding.tasteBtnNext.text = getString(R.string.common_complete)
        binding.tasteBtnNext.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.postFlavor()
            }
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setButtonUnavailable() {
        binding.tasteBtnNext.apply {
            setBackgroundResource(R.drawable.bg_btn_sub)
            isClickable = false
        }
    }

    private fun setButtonAvailable() {
        binding.tasteBtnNext.apply {
            setBackgroundResource(R.drawable.bg_btn_main)
            isClickable = true
        }
    }
}