package com.doctor.yumyum.presentation.ui.search.hashtag

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivitySearchHashtagBinding
import com.doctor.yumyum.presentation.adapter.WriteTagAdapter
import com.doctor.yumyum.presentation.ui.researchlist.ResearchListActivity
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteTagViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class SearchHashtagActivity :
    BaseActivity<ActivitySearchHashtagBinding>(R.layout.activity_search_hashtag) {

    private lateinit var tagAdapter: WriteTagAdapter
    private val viewModel: WriteTagViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTagRecycler()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.searchHashtagIbBack.setOnClickListener { finish() }
        binding.searchHashtagIbClose.setOnClickListener { binding.searchHashtagEtSearch.text.clear() }
        binding.searchHashtagEtSearch.setOnEditorActionListener { _, actionId, _ ->
            // 해시태그 추가
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (viewModel.tagList.value?.size ?: 0 >= 10) {
                    showToast(getString(R.string.search_hashtag_guide))
                } else {
                    viewModel.validTagItem()
                    binding.searchHashtagEtSearch.text.clear()
                }
            }
            false
        }
        // 레시피 목록으로 해시태그 리스트 전달
        binding.searchHashtagBtnSearch.setOnClickListener {
            val intent = Intent()
            intent.putStringArrayListExtra(ResearchListActivity.HASHTAG_EXTRA_KEY, viewModel.tagList.value)
            setResult(RESULT_OK, intent)
            finish()
        }

        viewModel.deleteStatus.observe(this) {
            tagAdapter.updateDeleteStatus(it)
        }
        viewModel.deleteTagList.observe(this) {
            tagAdapter.updateDeleteTagList(it)
        }
    }

    private fun initTagRecycler() {
        tagAdapter = WriteTagAdapter { tag ->
            String
            if (viewModel.deleteStatus.value == WriteTagViewModel.SELECT_DELETE_STATUS) {
                viewModel.updateDeleteTagList(tag)
            }
        }
        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let { layoutManager ->
            binding.searchHashtagRvTag.layoutManager = layoutManager
            binding.searchHashtagRvTag.adapter = tagAdapter
        }
    }
}