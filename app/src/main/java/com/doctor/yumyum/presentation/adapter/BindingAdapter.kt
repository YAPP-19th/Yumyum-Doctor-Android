package com.doctor.yumyum.presentation.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.R

@BindingAdapter("bind_tagList")
fun bindTagList(rvTagList: RecyclerView, tagList: ArrayList<String>?) {
    tagList?.run {
        ((rvTagList.adapter) as WriteTagAdapter).updateTagList(this)
    }
}

@BindingAdapter("bind_tagItem")
fun bindTagItem(tvTagItem: TextView, tagItem: String) {
    tvTagItem.text = "#$tagItem"
}

@BindingAdapter("bind_startCompat")
fun bindStartCompat(textView: TextView, condition: Boolean) {
    val src = if (condition) ContextCompat.getDrawable(textView.context, R.drawable.ic_report_selected) else ContextCompat.getDrawable(textView.context, R.drawable.ic_report_unselected)
    textView.setCompoundDrawablesWithIntrinsicBounds(src, null, null, null)
}
