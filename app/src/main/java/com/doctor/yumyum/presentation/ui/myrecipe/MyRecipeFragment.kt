package com.doctor.yumyum.presentation.ui.myrecipe

import ResearchListAdapter
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.databinding.DialogMyRecipeSortBinding
import com.doctor.yumyum.databinding.FragmentMyRecipeBinding
import com.doctor.yumyum.presentation.adapter.MyRecipeFavoriteAdapter
import com.doctor.yumyum.presentation.ui.myrecipe.viewmodel.MyRecipeViewModel
import com.doctor.yumyum.presentation.ui.recipedetail.RecipeDetailActivity
import com.doctor.yumyum.presentation.ui.write.WriteFragment2
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyRecipeFragment : BaseFragment<FragmentMyRecipeBinding>(R.layout.fragment_my_recipe) {
    private lateinit var filterLauncher: ActivityResultLauncher<Intent>
    private val myRecipeViewModel: MyRecipeViewModel by viewModels()
    private lateinit var sortSelectDialog: BottomSheetDialog
    private lateinit var sortSelectBinding: DialogMyRecipeSortBinding
    private lateinit var sortSelectView: View

    companion object {
        const val MODE = "mode"
        const val MIN = "minPrice"
        const val MAX = "maxPrice"
        const val STATUS = "status"
        const val CATEGORY = "category"
        const val TASTE = "tasteList"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initDialog()
        initFavoriteRecipeRecycler()
        startForFilter()

        myRecipeViewModel.mode.observe(viewLifecycleOwner) { mode ->
            binding.myRecipeIbMode.setImageResource(
                if (mode == R.string.common_food) R.drawable.ic_change_food else R.drawable.ic_change_beverage
            )
            val categoryName = requireContext().resources.getString(mode)
            getFavoriteList(categoryName)
            getMyPostList(categoryName)
        }
    }

    private fun initBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = myRecipeViewModel
        binding.myRecipeFragment = this
    }

    private fun initDialog() {
        sortSelectView = layoutInflater.inflate(R.layout.dialog_my_recipe_sort, null)
        sortSelectBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_my_recipe_sort,
            sortSelectView as ViewGroup,
            false
        )

        sortSelectBinding.apply {
            lifecycleOwner = this@MyRecipeFragment
        }

        sortSelectDialog = BottomSheetDialog(requireContext())
        sortSelectDialog.setContentView(sortSelectBinding.root)
    }

    private fun initMyRecipeRecycler(foodType: String) {
        val myRecipeListAdapter = ResearchListAdapter({ recipeId ->
            val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
            intent.putExtra("recipeId", recipeId)
            startActivity(intent)
        }, {}, {
            deleteBookMark(it.id)
        }, {
            postFavorite(it)
        }, foodType)
        binding.myRecipeRvPost.adapter = myRecipeListAdapter

        myRecipeViewModel.myRecipeList.observe(viewLifecycleOwner) {
            myRecipeListAdapter.setRecipeList(it)
        }
    }

    private fun initFavoriteRecipeRecycler() {
        val myRecipeFavoriteAdapter = MyRecipeFavoriteAdapter({ recipeId ->
            val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
            intent.putExtra("recipeId", recipeId)
            startActivity(intent)
        }, { recipeId ->
            CoroutineScope(Dispatchers.IO).launch {
                myRecipeViewModel.deleteFavorite(recipeId)
            }
        })
        binding.myRecipeRvFavoriteRecipe.adapter = myRecipeFavoriteAdapter

        myRecipeViewModel.favoriteRecipeList.observe(viewLifecycleOwner) {
            myRecipeFavoriteAdapter.setFavoriteList(it)
        }
    }

    private fun startForFilter() {
        filterLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val mode = it.data?.getIntExtra(MODE,0)?: 0
                    val minPrice = it.data?.getStringExtra(MIN)
                    val maxPrice = it.data?.getStringExtra(MAX)
                    val category = it.data?.getStringExtra(CATEGORY)
                    val status = it.data?.getStringExtra(STATUS)
                    val tasteList = it.data?.getStringArrayListExtra(TASTE) as ArrayList<String>
                    getMyPostWithFilter(mode,category,status,tasteList,minPrice,maxPrice)
                }
            }
        binding.myRecipeIbFilter.setOnClickListener {
            val intent = Intent(context, MyRecipeFilterActivity::class.java)
            filterLauncher.launch(intent)
        }
    }

    private fun getFavoriteList(category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            myRecipeViewModel.getFavoriteRecipe(category)
        }
    }

    private fun getMyPostList(category: String) {
        myRecipeViewModel.foodType.observe(viewLifecycleOwner) { foodType ->
            initMyRecipeRecycler(foodType)
            CoroutineScope(Dispatchers.IO).launch {
                myRecipeViewModel.getMyRecipe(category, foodType,null,null,null,null)
            }
        }
    }

    private fun getMyPostWithFilter(
        mode: Int,
        category: String?,
        status: String?,
        tasteList: ArrayList<String>,
        min: String?,
        max: String?
    ) {
        if(category.isNullOrBlank()){

        }
    }

    private fun postFavorite(it: RecipeModel) {
        myRecipeViewModel.mode.observe(viewLifecycleOwner) { mode ->
            val categoryName = requireContext().resources.getString(mode)
            CoroutineScope(Dispatchers.IO).launch {
                myRecipeViewModel.postFavorite(it.id, categoryName)
                myRecipeViewModel.getFavoriteRecipe(categoryName)
            }
        }
    }

    private fun deleteBookMark(recipeId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            myRecipeViewModel.deleteBookMark(recipeId)
        }
    }

    fun showBottomSheet() {
        sortSelectDialog.show()
    }
}