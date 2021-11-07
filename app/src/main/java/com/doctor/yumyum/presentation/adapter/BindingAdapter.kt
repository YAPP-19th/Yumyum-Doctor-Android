package com.doctor.yumyum.presentation.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("bind_tagList")
fun bindTagList(recyclerView: RecyclerView, tagList : ArrayList<String>){
    tagList.run {
        (recyclerView.adapter as? WriteTagAdapter)?.updateTagList(this)
    }
}