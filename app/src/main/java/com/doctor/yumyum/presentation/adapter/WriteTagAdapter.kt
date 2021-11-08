package com.doctor.yumyum.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.databinding.ItemInputIngredientBinding

class WriteTagAdapter : RecyclerView.Adapter<WriteTagAdapter.ViewHolder>(){
    var tagList: ArrayList<String> = arrayListOf()

    class ViewHolder(private val binding: ItemInputIngredientBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tagItem : String){
            binding.tagList = tagItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInputIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    fun updateTagList(newTag : String){
        tagList.add(0,newTag)
        notifyItemInserted(0)
    }
}