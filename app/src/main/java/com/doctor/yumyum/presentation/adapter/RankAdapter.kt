package com.doctor.yumyum.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doctor.yumyum.R
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.databinding.ItemResearchRankingBinding

class RankAdapter(private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<RankAdapter.ViewHolder>() {
    private val rankList: ArrayList<RecipeModel> = arrayListOf()

    inner class ViewHolder(private val binding: ItemResearchRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeModel) {
            binding.researchRankingTvRank.text = (adapterPosition + 1).toString()
            binding.researchRankingTvBrand.text = recipe.categoryName
            binding.researchRankingTvTitle.text = recipe.foodName
            binding.researchRankingTvCost.text = "${recipe.price}원"
            binding.researchRankingTvLike.text = recipe.numberOfLikes.toString()
            binding.root.setOnClickListener { itemClickListener(recipe.id) }

            if (recipe.foodImages.size > 0) {
                Glide
                    .with(binding.researchRankingIvRecipe)
                    .load(recipe.foodImages[0].imageUrl)
                    .into(binding.researchRankingIvRecipe)
            }

            when (adapterPosition) {
                0 -> binding.researchRankingIvRank.setImageResource(R.drawable.ic_gold_medal)
                1 -> binding.researchRankingIvRank.setImageResource(R.drawable.ic_silver_medal)
                2 -> binding.researchRankingIvRank.setImageResource(R.drawable.ic_bronze_medal)
                else -> binding.researchRankingIvRank.visibility = INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemResearchRankingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rankList[position])
    }

    override fun getItemCount(): Int = rankList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setRankList(newRankList: ArrayList<RecipeModel>) {
        rankList.clear()
        rankList.addAll(newRankList)
        notifyDataSetChanged()
    }
}