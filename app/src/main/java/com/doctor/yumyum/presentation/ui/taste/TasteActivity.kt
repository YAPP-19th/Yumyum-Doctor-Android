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
    private val tasteViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[TasteViewModel::class.java]
    }
    private var fromMyPage: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        tasteViewModel.isSelected.observe(this) { isSelected ->
            if (isSelected) setButtonAvailable()
            else setButtonUnavailable()
        }

        tasteViewModel.mode.observe(this) { mode ->
            if (mode == 0) setStateClass()
            else setStateDetail()
        }
    }

    fun init() {
        fromMyPage = intent.extras?.getBoolean(getString(R.string.taste_mode)) ?: false
        if (fromMyPage) {
            binding.tasteTvTitle.visibility = View.GONE
            binding.tasteTvSubtitle.setTextAppearance(R.style.font_h3_bold)
        }

        binding.apply {
            lifecycleOwner = this@TasteActivity
            viewModel = tasteViewModel
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
        if (tasteViewModel.tasteClassState.value?.size == 0) setButtonUnavailable()
        binding.tasteTvSubtitle.text = getString(R.string.taste_tv_class)
        binding.tasteBtnNext.text = getString(R.string.common_next)
        binding.tasteBtnNext.setOnClickListener {
            Navigation.findNavController(binding.tasteNav)
                .navigate(R.id.action_tasteClassFragment_to_tasteDetailFragment)
        }
    }

    private fun setStateDetail() {
        if (tasteViewModel.tasteDetailState.value?.size == 0) setButtonUnavailable()
        binding.tasteTvSubtitle.text = getString(R.string.taste_tv_detail)
        binding.tasteBtnNext.text = getString(R.string.common_complete)
        binding.tasteBtnNext.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                tasteViewModel.postFlavor()
            }
            if (fromMyPage) {
                finish()
            } else {
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
            }
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