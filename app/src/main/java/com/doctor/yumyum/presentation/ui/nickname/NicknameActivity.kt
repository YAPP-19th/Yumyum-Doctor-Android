package com.doctor.yumyum.presentation.ui.nickname

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityNicknameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NicknameActivity : BaseActivity<ActivityNicknameBinding>(R.layout.activity_nickname) {
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[NicknameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        viewModel.nickname.observe(this) { nickname ->
            binding.nicknameEtNickname.setText(nickname)
        }
        initNickname()
    }

    private fun init() {
        binding.apply {
            viewModel = viewModel
            lifecycleOwner = this@NicknameActivity
        }

        binding.nicknameBtnComplete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.patchNickname(binding.nicknameEtNickname.text.toString())
            }
        }

        binding.nicknameEtNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                CoroutineScope(Dispatchers.IO).launch {
                    val unique = viewModel.validateNickname(p0.toString())
                    if (!unique) {
                        setMessageOverlap()
                    }
                }
                if (p0.isNullOrEmpty()) {
                    setMessageNull()
                }
                else if (p0.length >= 20) {
                    setMessageOverflow()
                }
                else {
                    setMessageSuccess()
                }
            }
        })
    }

    private fun initNickname() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getNickname()
        }
    }

    fun setMessageNull() {
        binding.nicknameTvMessage.visibility = View.INVISIBLE
        setButtonUnavailable()
    }

    fun setMessageSuccess() {
        binding.nicknameTvMessage.apply {
            visibility = View.VISIBLE
            setTextColor(getColor(R.color.sub_green))
            text = getString(R.string.nickname_tv_success)
        }
        setButtonAvailable()
    }

    fun setMessageOverlap() {
        binding.nicknameTvMessage.apply {
            visibility = View.VISIBLE
            setTextColor(getColor(R.color.main_orange))
            text = getString(R.string.nickname_tv_overlap)
        }
        setButtonUnavailable()
    }

    fun setMessageOverflow() {
        binding.nicknameTvMessage.apply {
            visibility = View.VISIBLE
            setTextColor(getColor(R.color.main_orange))
            text = getString(R.string.nickname_tv_overflow)
        }
        setButtonUnavailable()
    }

    fun setButtonUnavailable() {
        binding.nicknameBtnComplete.apply {
            setBackgroundResource(R.drawable.bg_btn_sub)
            isClickable = false
        }
    }

    fun setButtonAvailable() {
        binding.nicknameBtnComplete.apply {
            setBackgroundResource(R.drawable.bg_btn_main)
            isClickable = true
        }
    }
}