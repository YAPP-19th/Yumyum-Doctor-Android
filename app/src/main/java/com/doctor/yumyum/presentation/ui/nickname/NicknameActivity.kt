package com.doctor.yumyum.presentation.ui.nickname

import android.annotation.SuppressLint
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
            nicknameEtNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (p0.isNullOrEmpty()) {
                        setMessageNull()
                        setButtonUnavailable()
                    }
                    else {
                        if (p0.length >= 20) {
                            setMessageOverflow()
                            setButtonUnavailable()
                        } else {
                            setMessageSuccess()
                            setButtonAvailable()
                        }
                    }
                }
            })
        }
    }

    private fun initNickname() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getNickname()
        }
    }

    fun setMessageNull() {
        binding.nicknameTvMessage.visibility = View.INVISIBLE
    }

    fun setMessageSuccess() {
        binding.nicknameTvMessage.visibility = View.VISIBLE
        binding.nicknameTvMessage.setTextColor(getColor(R.color.sub_green))
        binding.nicknameTvMessage.text = getString(R.string.nickname_tv_success)
    }

    fun setMessageOverlap() {
        binding.nicknameTvMessage.visibility = View.VISIBLE
        binding.nicknameTvMessage.setTextColor(getColor(R.color.main_orange))
        binding.nicknameTvMessage.text = getString(R.string.nickname_tv_overlap)
    }

    fun setMessageOverflow() {
        binding.nicknameTvMessage.visibility = View.VISIBLE
        binding.nicknameTvMessage.setTextColor(getColor(R.color.main_orange))
        binding.nicknameTvMessage.text = getString(R.string.nickname_tv_overflow)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setButtonUnavailable() {
        Log.d("로그", "Unavailable")
        binding.nicknameBtnComplete.apply {
            setBackgroundResource(R.drawable.bg_btn_sub)
            isClickable = false
            background = getDrawable(R.drawable.bg_btn_sub)
        }
    }

    fun setButtonAvailable() {
        Log.d("로그", "Unavailable")
        binding.nicknameBtnComplete.setBackgroundResource(R.drawable.bg_btn_main)
        binding.nicknameBtnComplete.isClickable = true
    }
}