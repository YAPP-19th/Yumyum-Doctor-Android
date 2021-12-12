package com.doctor.yumyum.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.R
import com.doctor.yumyum.databinding.ItemInputIngredientBinding
import com.doctor.yumyum.presentation.ui.login.LoginViewModel
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteTagViewModel

class WriteTagAdapter(private val itemClickListener: (String) -> Unit) :
    RecyclerView.Adapter<WriteTagAdapter.ViewHolder>() {
    private var tagList: ArrayList<String> = arrayListOf()
    private var deleteTagList : ArrayList<String> = arrayListOf()
    private var deleteStatus = 0

    inner class ViewHolder(private val binding: ItemInputIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")
        fun bind(tagItem: String) {
            binding.tagItem = tagItem
            binding.root.setOnClickListener {
                itemClickListener(tagItem)
                if(deleteStatus == 1001){
                    if(deleteTagList.contains(tagItem)){
                        binding.itemIngredientsTvTag.setTextColor(it.context.getColor(R.color.main_orange))
                        binding.itemIngredientsTvTag.background = it.context.getDrawable(R.drawable.bg_select_tag_item)
                    }else{
                        binding.itemIngredientsTvTag.setTextColor(it.context.getColor(R.color.dark_gray))
                        binding.itemIngredientsTvTag.background = it.context.getDrawable(R.drawable.bg_unselect_tag_item)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemInputIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    fun updateDeleteTagList(newDeleteList :ArrayList<String>){
        deleteTagList.clear()
        deleteTagList.addAll(newDeleteList)
        Log.d("Adapter", " tagList :${deleteTagList}")
    }

    fun updateDeleteStatus(newStatus : Int){
        deleteStatus = newStatus
        Log.d("Adapter","status :${deleteStatus}")
    }
}