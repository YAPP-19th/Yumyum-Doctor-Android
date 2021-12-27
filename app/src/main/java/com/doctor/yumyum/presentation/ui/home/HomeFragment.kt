package com.doctor.yumyum.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentHomeBinding
import com.doctor.yumyum.presentation.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwnerg

        viewModel.mode.observe(viewLifecycleOwner) {
            binding.homeIbMode.setImageResource(
                if (it == R.string.common_food) R.drawable.ic_change_food else R.drawable.ic_change_beverage)
        }
        viewModel.nickname.observe(viewLifecycleOwner) {
            binding.homeTvGreeting.text = "안녕하세요, $it 학생!"
        }

        binding.homeBtnMyRecipe.setOnClickListener {
            val activity = activity as MainActivity
            activity.replaceMyRecipe()
        }

        initNickname()
    }

    private fun initNickname() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getUserNickname()
        }
    }
}