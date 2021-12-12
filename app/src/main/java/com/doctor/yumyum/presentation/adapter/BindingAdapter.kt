package com.doctor.yumyum.presentation.adapter

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.R

@BindingAdapter("bind_tagList")
fun bindTagList(rvTagList: RecyclerView, tagList : ArrayList<String>?){
    tagList?.run {
        ((rvTagList.adapter) as WriteTagAdapter).updateTagList(this)
    }
}

@BindingAdapter("bind_tagItem")
fun bindTagItem(tvTagItem: TextView, tagItem : String){
    tvTagItem.text= "#$tagItem"
    tvTagItem.setTextColor(tvTagItem.context.getColor(R.color.dark_gray))
    tvTagItem.background = tvTagItem.context.getDrawable(R.drawable.bg_unselect_tag_item)
}