package com.doctor.yumyum.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doctor.yumyum.R
import com.doctor.yumyum.data.model.FoodImage
import com.doctor.yumyum.databinding.ItemRecipeDetailImageBinding

class RecipeDetailAdapter : RecyclerView.Adapter<RecipeDetailAdapter.ViewHolder>() {
    private val imageList: ArrayList<FoodImage> = arrayListOf()

    inner class ViewHolder(private val binding: ItemRecipeDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foodImage: FoodImage) {

            Glide
                .with(binding.itemRecipeDetailIvImage)
                .load(foodImage.imageUrl)
                .placeholder(R.drawable.ic_loading_image)
                .into(binding.itemRecipeDetailIvImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecipeDetailImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setImageList(newImageList: ArrayList<FoodImage>) {
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()
    }
}