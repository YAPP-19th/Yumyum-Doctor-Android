package com.doctor.yumyum.presentation.ui.filter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseActivity
import com.doctor.yumyum.databinding.ActivityFilterBinding

class FilterActivity : BaseActivity<ActivityFilterBinding>(R.layout.activity_filter) {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 현재 필터 설정되어 있는 값 보여주기
        intent.extras?.get("minPrice")?.let { binding.filterEtPriceLower.setText(it.toString()) }
        intent.extras?.get("maxPrice")?.let { binding.filterEtPriceUpper.setText(it.toString()) }

        // 뒤로가기
        binding.filterAppbar.appbarIbBack.setOnClickListener {
            finish()
        }

        // 필터 선택 완료
        binding.filterBtnSelect.setOnClickListener {
            val minPrice = if (binding.filterEtPriceLower.text.toString().isEmpty()) null
            else binding.filterEtPriceLower.text.toString().toInt()
            val maxPrice = if (binding.filterEtPriceUpper.text.toString().isEmpty()) null
            else binding.filterEtPriceUpper.text.toString().toInt()

            if (minPrice == null && maxPrice == null) {
                // 최소 가격과 최대 가격 중 하나는 입력해야 합니다.
                showToast(getString(R.string.error_filter_price_empty))
            } else if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                // 최소 가격이 최대 가격보다 큰 경우
                showToast(getString(R.string.error_filter_price_range))
            } else {
                // 레시피 목록으로 최소 가격과 최대 가격 전달
                setFilterResult(minPrice, maxPrice)
            }
        }

        // 필터 초기화
        binding.filterIbReset.setOnClickListener { setFilterResult(null, null) }

        // edit text 입력 중 배경 변경
        val focusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            view.background =
                if (hasFocus) getDrawable(R.drawable.bg_price_edited)
                else getDrawable(R.drawable.bg_price_unedited)
        }
        binding.filterEtPriceLower.onFocusChangeListener = focusChangeListener
        binding.filterEtPriceUpper.onFocusChangeListener = focusChangeListener
    }

    private fun setFilterResult(minPrice: Int?, maxPrice: Int?) {
        val intent = Intent(this, FilterActivity::class.java)
        intent.putExtra("minPrice", minPrice)
        intent.putExtra("maxPrice", maxPrice)
        setResult(RESULT_OK, intent)
        finish()
    }
}