package com.doctor.yumyum.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.databinding.ItemHomeBrandBinding
import com.doctor.yumyum.databinding.ItemResearchBrandBinding

class HomeBrandAdapter(private val itemClickListener: (String) -> Unit) :
    RecyclerView.Adapter<HomeBrandAdapter.ViewHolder>() {

    private var brandList: MutableList<Pair<Int, String>> = mutableListOf()

    inner class ViewHolder(private val binding: ItemHomeBrandBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(brand: Pair<Int, String>) {
                binding.brandName = brand.second
                binding.itemHomeBrandIvBrand.setImageResource(brand.first)
                binding.root.setOnClickListener { itemClickListener(brand.second) }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeBrandBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(brandList[position])
    }

    override fun getItemCount(): Int = brandList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setBrandList(brandList: MutableList<Pair<Int, String>>) {
        this.brandList = brandList
        notifyDataSetChanged()
    }
}