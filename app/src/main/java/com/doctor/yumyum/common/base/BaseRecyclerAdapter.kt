package com.doctor.yumyum.common.base

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter :
    RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder>() {
    private val items: ArrayList<Any> = arrayListOf()

    abstract class ViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        abstract fun setItem(item: Any)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}