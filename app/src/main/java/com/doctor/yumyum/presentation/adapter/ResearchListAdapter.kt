import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doctor.yumyum.data.model.RankRecipe
import com.doctor.yumyum.databinding.ItemResearchRecipeBinding

class ResearchListAdapter(
    private val itemClickListener: (Int) -> Unit,
    private val deviceHeight: Int
) :
    RecyclerView.Adapter<ResearchListAdapter.ViewHolder>() {
    private val recipeList: ArrayList<RankRecipe> = arrayListOf()

    inner class ViewHolder(private val binding: ItemResearchRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RankRecipe) {
            binding.itemResearchRecipeIvImage.layoutParams.height = deviceHeight
            binding.itemResearchRecipeTvBrand.text = recipe.categoryName
            binding.itemResearchRecipeTvTitle.text = recipe.foodName
            binding.itemResearchRecipeTvCost.text = "${recipe.price}원"
            binding.root.setOnClickListener { itemClickListener(recipe.id) }

            if (recipe.foodImages.size > 0) {
                Glide
                    .with(binding.itemResearchRecipeIvImage)
                    .load(recipe.foodImages[0].imageUrl)
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
    fun setRecipeList(newRecipeList: ArrayList<RankRecipe>) {
        recipeList.clear()
        recipeList.addAll(newRecipeList)
        notifyDataSetChanged()
    }
}