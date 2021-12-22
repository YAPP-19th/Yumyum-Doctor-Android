package com.doctor.yumyum.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.databinding.ItemSearchTasteBinding

class TasteTagAdapter :
    RecyclerView.Adapter<TasteTagAdapter.ViewHolder>() {
    private var tagList: ArrayList<String> = arrayListOf()

    inner class ViewHolder(private val binding: ItemSearchTasteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tagItem: String) {
            binding.tagItem = tagItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchTasteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    fun updateTagList(newTagList: ArrayList<String>) {
        tagList.clear()
        tagList.addAll(newTagList)
        notifyDataSetChanged()
    }
}