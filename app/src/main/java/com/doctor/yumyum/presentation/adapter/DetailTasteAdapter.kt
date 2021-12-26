package com.doctor.yumyum.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doctor.yumyum.R
import com.doctor.yumyum.databinding.ItemWriteDetailTasteBinding


class DetailTasteAdapter(private val itemClickListener: (View) -> Unit) : RecyclerView.Adapter<DetailTasteAdapter.ViewHolder>() {
    private var detailTasteList : MutableList<Pair<Int, String>> = mutableListOf()
    private var selectedTasteList : ArrayList<String> = arrayListOf()

    inner class ViewHolder(private val binding: ItemWriteDetailTasteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(taste: Pair<Int, String>) {
            binding.itemTvDetailTaste.apply {
                this.text= taste.second
                this.setCompoundDrawablesWithIntrinsicBounds(binding.root.context.getDrawable(taste.first),null,null,null)
                this.setOnClickListener {
                    itemClickListener(this)
                    if(selectedTasteList.contains(taste.second)){
                        this.background = it.context.getDrawable(R.drawable.bg_filter_taste_selected)
                        this.setTextColor(it.context.getColor(R.color.black))
                    }else{
                        this.background = it.context.getDrawable(R.drawable.bg_filter_taste_unselected)
                        this.setTextColor(it.context.getColor(R.color.gray))
                    }
                }
            }
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

    fun updateSelectedList(newSelectedList : ArrayList<String>){
        selectedTasteList = newSelectedList
    }
}