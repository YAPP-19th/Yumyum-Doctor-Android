import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doctor.yumyum.R
import com.doctor.yumyum.common.utils.RecipeType
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.databinding.ItemResearchRecipeBinding

class ResearchListAdapter(
    private val itemClickListener: (Int) -> Unit,
    private val bookmarkClickListener: (RecipeModel) -> Unit,
    private val favoriteClickListener: (RecipeModel) -> Unit,
    private val recipeType : String
) :
    RecyclerView.Adapter<ResearchListAdapter.ViewHolder>() {
    private val recipeList: ArrayList<RecipeModel> = arrayListOf()

    inner class ViewHolder(private val binding: ItemResearchRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeModel) {
            when(recipeType){
                RecipeType.MYFOOD.name -> binding.itemResearchRecipeIbBookmark.visibility = View.GONE
                RecipeType.BASIC.name -> binding.itemRecipeIbFavorite.visibility = View.GONE
            }
            binding.itemResearchRecipeTvBrand.text = recipe.categoryName
            binding.itemResearchRecipeTvTitle.text = recipe.foodName
            binding.itemResearchRecipeTvCost.text = "${recipe.price}원"
            binding.root.setOnClickListener { itemClickListener(recipe.id) }
            binding.itemResearchRecipeIbBookmark.setOnClickListener { bookmarkClickListener(recipe) }
            binding.itemRecipeIbFavorite.setOnClickListener { favoriteClickListener(recipe)}

            if (recipe.isBookmark)
                binding.itemResearchRecipeIbBookmark.setImageResource(R.drawable.ic_bookmark_clicked)
            else
                binding.itemResearchRecipeIbBookmark.setImageResource(R.drawable.ic_bookmark_unclicked)

            if(recipe.isFavorite)
                binding.itemRecipeIbFavorite.setImageResource(R.drawable.ic_heart_selected)
            else
                binding.itemRecipeIbFavorite.setImageResource(R.drawable.ic_heart_unselected)

            if (recipe.foodImages.size > 0) {
                Glide
                    .with(binding.itemResearchRecipeIvImage)
                    .load(recipe.foodImages[0].imageUrl)
                    .placeholder(R.drawable.ic_loading_image)
                    .into(binding.itemResearchRecipeIvImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemResearchRecipeBinding.inflate(
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