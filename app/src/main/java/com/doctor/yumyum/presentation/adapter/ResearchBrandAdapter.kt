package com.doctor.yumyum.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.R
import com.doctor.yumyum.databinding.ItemResearchBrandBinding

class ResearchBrandAdapter(private val itemClickListener: (String) -> Unit) :
    RecyclerView.Adapter<ResearchBrandAdapter.ViewHolder>() {
    private var brandList: MutableList<Pair<Int, String>> = mutableListOf()

    class ViewHolder(private val binding: ItemResearchBrandBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(brand: Pair<Int, String>) {
            binding.brandIvLogo.setImageResource(brand.first)
            binding.brandName = brand.second
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemResearchBrandBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(brandList[position])
        holder.itemView.setOnClickListener { itemClickListener(brandList[position].second) }
    }

    override fun getItemCount(): Int = brandList.size

    fun setBrandList(brandList: MutableList<Pair<Int, String>>) {
        this.brandList = brandList
    }
}