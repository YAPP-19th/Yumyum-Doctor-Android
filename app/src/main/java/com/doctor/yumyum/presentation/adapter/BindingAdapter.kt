package com.doctor.yumyum.presentation.adapter

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("bind_tagList")
fun bindTagList(rvTagList: RecyclerView, tagList : ArrayList<String>?){
    tagList?.run {
        ((rvTagList.adapter) as WriteTagAdapter).updateTagList(this)
    }
}

@BindingAdapter("bind_tagItem")
fun bindTagItem(tvTagItem: TextView, tagItem : String){
    tvTagItem.text= "#$tagItem"
}