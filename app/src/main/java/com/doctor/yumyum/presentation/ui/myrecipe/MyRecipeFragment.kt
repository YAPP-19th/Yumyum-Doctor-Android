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
import com.doctor.yumyum.common.utils.SortType
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.databinding.DialogMyRecipeSortBinding
import com.doctor.yumyum.databinding.FragmentMyRecipeBinding
import com.doctor.yumyum.presentation.adapter.MyRecipeFavoriteAdapter
import com.doctor.yumyum.presentation.ui.myrecipe.viewmodel.MyRecipeViewModel
import com.doctor.yumyum.presentation.ui.recipedetail.RecipeDetailActivity
import com.doctor.yumyum.presentation.ui.researchlist.ResearchListViewModel
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

    private var category: String? = null
    private var sort = "id"
    private var order = "desc"
    private var minPrice: String? = null
    private var maxPrice: String? = null
    private var status: String? = null
    private var flavors: java.util.ArrayList<String> = arrayListOf("")

    private var isFilterSet: Boolean = false

    companion object {
        const val MIN = "minPrice"
        const val MAX = "maxPrice"
        const val STATUS = "status"
        const val CATEGORY = "category"
        const val TASTE = "tasteList"

        const val FILTER_APPLY = 1004
        const val FILTER_RESET = 1005
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initDialog()
        initFavoriteRecipeRecycler()
        startForFilter()

        //음식,음료 observe
        myRecipeViewModel.mode.observe(viewLifecycleOwner) { mode ->
            binding.myRecipeIbMode.setImageResource(
                if (mode == R.string.common_food) R.drawable.ic_change_food else R.drawable.ic_change_beverage
            )
            getFavoriteList(requireContext().resources.getString(mode))
            getMyPostWithFilter()
        }

        //정렬 적용하기 Observe
        myRecipeViewModel.sortType.observe(viewLifecycleOwner) { type ->
            when (type) {
                SortType.RECENT -> {
                    sort = "id"
                    order = "desc"
                }
                SortType.EXPENSIVE -> {
                    sort = "price"
                    order = "desc"
                }
                SortType.CHEAP -> {
                    sort = "price"
                    order = "asc"
                }
            }
            sortSelectDialog.dismiss()
            getMyPostWithFilter()
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
            viewModel = myRecipeViewModel
        }

        sortSelectDialog = BottomSheetDialog(requireContext())
        sortSelectDialog.setContentView(sortSelectBinding.root)
    }

    private fun initMyRecipeRecycler(foodType: String) {
        if (isFilterSet) {
            binding.myRecipeIbFilter.setImageResource(R.drawable.ic_filter_green)
        } else {
            binding.myRecipeIbFilter.setImageResource(R.drawable.ic_filter_gray)
        }
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
                //필터 적용 화면에서 돌아왔을때
                when (it.resultCode) {
                    FILTER_APPLY -> {
                        isFilterSet = true
                        minPrice = it.data?.getStringExtra(MIN)
                        maxPrice = it.data?.getStringExtra(MAX)
                        category = it.data?.getStringExtra(CATEGORY)
                        status = it.data?.getStringExtra(STATUS)
                        flavors = it.data?.getStringArrayListExtra(TASTE) as ArrayList<String>
                        getMyPostWithFilter()
                    }
                    FILTER_RESET -> {
                        isFilterSet = false
                        minPrice = null
                        maxPrice = null
                        category = null
                        status = null
                        flavors = arrayListOf()
                        getMyPostWithFilter()
                    }
                }

            }
        binding.myRecipeIbFilter.setOnClickListener {
            val intent = Intent(context, MyRecipeFilterActivity::class.java)
            intent.putExtra(MIN, minPrice)
            intent.putExtra(MAX, maxPrice)
            intent.putExtra(CATEGORY, category)
            intent.putExtra(STATUS, status)
            intent.putStringArrayListExtra(TASTE, flavors)
            filterLauncher.launch(intent)
        }
    }

    private fun getFavoriteList(category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            myRecipeViewModel.getFavoriteRecipe(category)
        }
    }

    private fun getMyPostWithFilter() {
        val categoryName = if (category.isNullOrBlank()) {
            myRecipeViewModel.mode.value?.let { requireContext().resources.getString(it) }
        } else {
            category
        }
        myRecipeViewModel.foodType.observe(viewLifecycleOwner) { foodType ->
            initMyRecipeRecycler(foodType)
            CoroutineScope(Dispatchers.IO).launch {
                myRecipeViewModel.getMyRecipe(categoryName.toString(), foodType,null,minPrice, maxPrice, status,sort,order)
            }
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
        myRecipeViewModel.initSortType()
    }

    fun changeFoodType(foodType: String) {
        isFilterSet = false
        myRecipeViewModel.changeFoodType(foodType)
    }
}