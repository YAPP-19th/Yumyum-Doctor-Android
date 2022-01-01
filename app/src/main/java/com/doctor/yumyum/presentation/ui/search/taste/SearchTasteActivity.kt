package com.doctor.yumyum.presentation.ui.search.taste

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivitySearchTasteBinding

class SearchTasteActivity :
    BaseActivity<ActivitySearchTasteBinding>(R.layout.activity_search_taste) {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SearchTasteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.searchTasteAppbar.appbarIbBack.setOnClickListener { finish() }
        binding.searchTasteBtnSearch.setOnClickListener {
            val intent = Intent()
            intent.putStringArrayListExtra("taste list", viewModel.tasteList.value)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}