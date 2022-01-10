package com.doctor.yumyum.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doctor.yumyum.R
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.databinding.ItemHomeTodayBinding


class HomeTodayAdapter(private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<HomeTodayAdapter.ViewHolder>() {
    private val recipeList: ArrayList<RecipeModel> = arrayListOf()

    inner class ViewHolder(private val binding: ItemHomeTodayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeModel) {
            binding.itemHomeTodayTvRecipe.text = recipe.foodName
            binding.itemHomeTodayTvBrand.text = recipe.categoryName
            binding.itemHomeTodayTvPrice.text =
                binding.root.context.getString(R.string.common_variable_price, recipe.price)
            binding.itemHomeTodayTvLike.text = recipe.numberOfLikes.toString()
            binding.root.setOnClickListener { itemClickListener(recipe.id) }

            if (recipe.foodImages.size > 0) {
                Glide
                    .with(binding.itemHomeTodayIvRecipe)
                    .load(recipe.foodImages[0].imageUrl)
                    .placeholder(R.drawable.ic_loading_image)
                    .into(binding.itemHomeTodayIvRecipe)
            } else {
                binding.itemHomeTodayIvRecipe.setImageResource(R.drawable.ic_loading_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeTodayBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    override fun getItemCount(): Int = recipeList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setRecipeList(newRecipeList: ArrayList<RecipeModel>) {
        recipeList.clear()
        recipeList.addAll(newRecipeList)
        notifyDataSetChanged()
    }
}