package com.doctor.yumyum.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.data.model.FavoriteRecipe
import com.doctor.yumyum.data.remote.response.FavoriteRecipeResponse
import com.doctor.yumyum.databinding.DialogWriteBinding
import com.doctor.yumyum.databinding.ItemMyrecipeFavoriteBinding
import com.doctor.yumyum.databinding.ItemSearchTasteBinding

class MyRecipeFavoriteAdapter(private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<MyRecipeFavoriteAdapter.ViewHolder>() {
    private val favoriteList: ArrayList<FavoriteRecipe> = arrayListOf()

    inner class ViewHolder(private val binding: ItemMyrecipeFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: FavoriteRecipe) {
                binding.
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMyrecipeFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteList(newList: ArrayList<FavoriteRecipe>) {
        favoriteList.clear()
        favoriteList.addAll(newList)
        notifyDataSetChanged()
    }
}