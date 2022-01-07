package com.doctor.yumyum.presentation.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.doctor.yumyum.R
import com.doctor.yumyum.data.model.FavoriteRecipe
import com.doctor.yumyum.data.model.RecipeModel

@BindingAdapter("bind_tagList")
fun bindTagList(rvTagList: RecyclerView, tagList: ArrayList<String>?) {
    tagList?.run {
        ((rvTagList.adapter) as WriteTagAdapter).updateTagList(this)
    }
}

@BindingAdapter("bind_tasteTagList")
fun bindTasteTagList(rvTagList: RecyclerView, tagList: ArrayList<String>?) {
    tagList?.run {
        ((rvTagList.adapter) as TasteTagAdapter).updateTagList(this)
    }
}

@BindingAdapter("bind_rankList")
fun bindRankList(rvTagList: RecyclerView, rankList: ArrayList<RecipeModel>?) {

    rankList?.run {
        ((rvTagList.adapter) as RankAdapter).setRankList(this)
    }
}

@BindingAdapter("bind_tagItem")
fun bindTagItem(tvTagItem: TextView, tagItem: String) {
    tvTagItem.text = tvTagItem.context.getString(R.string.common_tagItem,tagItem)
    tvTagItem.setTextColor(tvTagItem.context.getColor(R.color.dark_gray))
    tvTagItem.background = tvTagItem.context.getDrawable(R.drawable.bg_unselect_tag_item)
}

@BindingAdapter("bind_startCompat")
fun bindStartCompat(textView: TextView, condition: Boolean) {
    val src = if (condition) ContextCompat.getDrawable(
        textView.context,
        R.drawable.ic_report_selected
    ) else ContextCompat.getDrawable(textView.context, R.drawable.ic_report_unselected)
    textView.setCompoundDrawablesWithIntrinsicBounds(src, null, null, null)
}

@BindingAdapter("bind_withdraw")
fun bindWithdraw(textView: TextView, condition: Boolean) {
    val src = if (condition) ContextCompat.getDrawable(
        textView.context,
        R.drawable.ic_withdraw_selected
    ) else ContextCompat.getDrawable(textView.context, R.drawable.ic_withdraw_unselected)
    textView.setCompoundDrawablesWithIntrinsicBounds(src, null, null, null)
}

@BindingAdapter("bind_tasteDetail")
fun bindTasteDetail(button: Button, tasteList: List<String>) {
    if (tasteList.contains(button.text)) {
        button.background =
            ContextCompat.getDrawable(button.context, R.drawable.bg_taste_detail_selected)
    } else {
        button.background =
            ContextCompat.getDrawable(button.context, R.drawable.bg_taste_detail_unselected)
    }
}

@BindingAdapter("bind_brandBackground")
fun bindBrandBackGround(textView: TextView, tempCategory: String?) {
    if (textView.text == tempCategory) {
        textView.background = ContextCompat.getDrawable(textView.context, R.drawable.bg_tv_select)
    } else {
        textView.background = null
    }
}

@BindingAdapter("bind_homeFavorite")
fun bindHomeFavorite(rvRecipeList:RecyclerView, recipeList:ArrayList<FavoriteRecipe>?) {
    recipeList?.run {
        ((rvRecipeList.adapter) as HomeFavoriteAdapter).setRecipeList(recipeList)
    }
}

@BindingAdapter("bind_recommendationList")
fun bindRecommendationList(
    vpRecommendation: ViewPager2,
    recommendationList: ArrayList<RecipeModel>?
) {
    recommendationList?.run {
        ((vpRecommendation.adapter) as HomeTodayAdapter).setRecipeList(this)
    }
}

@BindingAdapter("bind_writeImg")
fun bindWriteImg(imageButton: ImageButton, imgUri: Pair<Uri, String>?) {
    imgUri?.apply {
        imageButton.setImageURI(this.first)
    }

}

@BindingAdapter("bind_filterBackground")
fun bindFilterBackGround(textView: TextView, category: String?) {
    if (textView.text == category) {
        textView.background = ContextCompat.getDrawable(textView.context, R.drawable.bg_filter_taste_selected)
        textView.setTextColor(textView.context.getColor(R.color.black))
    } else {
        textView.background = ContextCompat.getDrawable(textView.context, R.drawable.bg_filter_taste_unselected)
        textView.setTextColor(textView.context.getColor(R.color.gray))
    }
}
