package com.doctor.yumyum.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doctor.yumyum.R
import com.doctor.yumyum.common.utils.RecipeType
import com.doctor.yumyum.data.model.FavoriteRecipe
import com.doctor.yumyum.data.remote.response.FavoriteRecipeResponse
import com.doctor.yumyum.databinding.DialogWriteBinding
import com.doctor.yumyum.databinding.ItemMyrecipeFavoriteBinding
import com.doctor.yumyum.databinding.ItemSearchTasteBinding

class MyRecipeFavoriteAdapter(
    private val itemClickListener: (Int) -> Unit,
    private val deleteFavorite: (Int) -> Unit
) :
    RecyclerView.Adapter<MyRecipeFavoriteAdapter.ViewHolder>() {
    private val favoriteList: ArrayList<FavoriteRecipe> = arrayListOf()

    inner class ViewHolder(private val binding: ItemMyrecipeFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: FavoriteRecipe, position: Int) {
            binding.itemMyFavoriteTvBrand.text = recipe.categoryName
            binding.itemMyFavoriteTvPrice.text =
                binding.root.resources.getString(R.string.common_variable_price, recipe.price)
            binding.itemMyFavoriteTvTitle.text = recipe.foodTitle

            if (recipe.meFavorite) {
                binding.itemMyFavoriteIbHeart.setImageResource(R.drawable.ic_heart_selected)
            } else {
                binding.itemMyFavoriteIbHeart.setImageResource(R.drawable.ic_heart_unselected)
            }

            if (recipe.foodImages.isNotEmpty()) {
                Glide
                    .with(binding.itemMyFavoriteIvImage)
                    .load(recipe.foodImages[0].imageUrl)
                    .placeholder(R.drawable.ic_loading_image)
                    .into(binding.itemMyFavoriteIvImage)
            }

            binding.root.setOnClickListener { itemClickListener(recipe.id)}
            binding.itemMyFavoriteIbHeart.setOnClickListener {
                deleteFavorite(recipe.id)
                favoriteList.removeAt(position)
                notifyItemRemoved(position)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMyrecipeFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoriteList[position],position)
    }

    override fun getItemCount(): Int = favoriteList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteList(newList: ArrayList<FavoriteRecipe>) {
        favoriteList.clear()
        favoriteList.addAll(newList)
        notifyDataSetChanged()
    }
}