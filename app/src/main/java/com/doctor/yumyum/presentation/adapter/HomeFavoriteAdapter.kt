package com.doctor.yumyum.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doctor.yumyum.R
import com.doctor.yumyum.data.model.FavoriteRecipe
import com.doctor.yumyum.databinding.ItemHomeFavoriteBinding

class HomeFavoriteAdapter(private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<HomeFavoriteAdapter.ViewHolder>() {

    private var recipeList: ArrayList<FavoriteRecipe> = arrayListOf()

    inner class ViewHolder(private val binding: ItemHomeFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: FavoriteRecipe) {
            binding.homeFavoriteTvBrand.text = recipe.categoryName
            binding.homeFavoriteTvName.text = recipe.foodTitle
            binding.homeFavoriteTvPrice.text = "${recipe.price}원"

            if (recipe.foodImages.isNotEmpty()) {
                Glide
                    .with(binding.homeFavoriteIvRecipe)
                    .load(recipe.foodImages[0].imageUrl)
                    .into(binding.homeFavoriteIvRecipe)
            }
            binding.root.setOnClickListener {
                itemClickListener(recipe.id)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeFavoriteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    override fun getItemCount(): Int = recipeList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setRecipeList(newRecipeList: ArrayList<FavoriteRecipe>) {
        recipeList.clear()
        recipeList.addAll(newRecipeList)
        notifyDataSetChanged()
    }
}
