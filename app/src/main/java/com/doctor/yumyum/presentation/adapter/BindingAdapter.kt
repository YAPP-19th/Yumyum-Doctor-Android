package com.doctor.yumyum.presentation.adapter

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("bind_tagList")
fun bindTagList(recyclerView: RecyclerView, tagItem : String?){
    tagItem?.run {
        ((recyclerView.adapter) as WriteTagAdapter).updateTagList("#$this")
    }
}