package com.doctor.yumyum.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.R
import com.doctor.yumyum.databinding.ItemResearchBrandBinding
import com.doctor.yumyum.databinding.ItemWriteDetailTasteBinding


class DetailTasteAdapter(private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<DetailTasteAdapter.ViewHolder>() {
    private var detailTasteList : MutableList<Pair<Int, String>> = mutableListOf()

    inner class ViewHolder(private val binding: ItemWriteDetailTasteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(taste: Pair<Int, String>) {
            Log.d("Adapter : bind",taste.second)
            binding.itemTvDetailTaste.text = taste.second
            binding.itemTvDetailTaste.setCompoundDrawablesWithIntrinsicBounds(binding.root.context.getDrawable(taste.first),null,null,null)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWriteDetailTasteBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(detailTasteList[position])
    }

    override fun getItemCount(): Int = detailTasteList.size

    fun setTasteList(tasteList: MutableList<Pair<Int, String>>) {
        detailTasteList = tasteList
    }
}