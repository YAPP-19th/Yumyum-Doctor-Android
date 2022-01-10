package com.doctor.yumyum.presentation.ui.myrecipe

import ResearchListAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.common.utils.ORDER_FLAG
import com.doctor.yumyum.common.utils.RecipeType
import com.doctor.yumyum.common.utils.SORT_FLAG
import com.doctor.yumyum.common.utils.SortType
import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.databinding.DialogMyRecipeSortBinding
import com.doctor.yumyum.databinding.FragmentMyRecipeBinding
import com.doctor.yumyum.presentation.adapter.MyRecipeFavoriteAdapter
import com.doctor.yumyum.presentation.ui.myrecipe.viewmodel.MyRecipeViewModel
import com.doctor.yumyum.presentation.ui.recipedetail.RecipeDetailActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.min


class MyRecipeFragment : BaseFragment<FragmentMyRecipeBinding>(R.layout.fragment_my_recipe) {
    private lateinit var filterLauncher: ActivityResultLauncher<Intent>
    private lateinit var detailLauncher: ActivityResultLauncher<Intent>
    private val myRecipeViewModel: MyRecipeViewModel by viewModels()
    private lateinit var sortSelectDialog: BottomSheetDialog
    private lateinit var sortSelectBinding: DialogMyRecipeSortBinding
    private lateinit var sortSelectView: View

    private lateinit var myRecipeFavoriteAdapter: MyRecipeFavoriteAdapter
    private lateinit var myRecipeListAdapter: ResearchListAdapter
    private var mode: Int = 0
    private var category: String? = null
    private var sort = "id"
    private var order = "desc"
    private var minPrice: String? = null
    private var maxPrice: String? = null
    private var status: String? = null
    private var flavors: java.util.ArrayList<String> = arrayListOf("")
    private var foodType: String = RecipeType.MYFOOD.name

    private var isFilterSet: Boolean = false

    companion object {
        const val MIN_KEY = "minPrice"
        const val MAX_KEY = "maxPrice"
        const val STATUS_KEY = "status"
        const val CATEGORY_KEY = "category"
        const val TASTE_KEY = "tasteList"

        const val FILTER_APPLY = 1004
        const val FILTER_RESET = 1005
        const val DELETE_RECIPE = 1006
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initDialog()
        initAdapter()
        startForFilter()

        //홈에서 나의레시피 가져오기
        arguments?.getString(resources.getString(R.string.common_brand))?.let {
            category = it
            isFilterSet = true
        }

        //음식,음료 observe
        myRecipeViewModel.mode.observe(viewLifecycleOwner) { mode ->
            binding.myRecipeIbMode.setImageResource(
                if (mode == R.string.common_food) R.drawable.ic_change_food else R.drawable.ic_change_beverage
            )

            this.mode = mode
            getMyFavorite()
            getMyPostWithFilter()
        }

        //내가쓴글, 스크랩 observe
        myRecipeViewModel.foodType.observe(viewLifecycleOwner) {
            foodType = it
            getMyPostWithFilter()
        }

        //내가쓴 글 리스트 observe
        myRecipeViewModel.myRecipeList.observe(viewLifecycleOwner) {
            myRecipeListAdapter.setRecipeList(it)
        }

        //최애리스트 observe
        myRecipeViewModel.favoriteRecipeList.observe(viewLifecycleOwner) {
            myRecipeFavoriteAdapter.setFavoriteList(it)
        }

        //정렬 적용하기 Observe
        myRecipeViewModel.sortType.observe(viewLifecycleOwner) { type ->
            when (type) {
                SortType.RECENT -> {
                    sort = SORT_FLAG.ID
                    order = ORDER_FLAG.DESC
                    binding.myRecipeIbSort.setImageResource(R.drawable.ic_sort_gray)
                }
                SortType.EXPENSIVE -> {
                    sort = SORT_FLAG.PRICE
                    order = ORDER_FLAG.DESC
                    binding.myRecipeIbSort.setImageResource(R.drawable.ic_sort_green)
                }
                SortType.CHEAP -> {
                    sort = SORT_FLAG.PRICE
                    order = ORDER_FLAG.ASC
                    binding.myRecipeIbSort.setImageResource(R.drawable.ic_sort_green)
                }
            }
            sortSelectDialog.dismiss()
            getMyPostWithFilter()
        }

        //Detail Launcher
        detailLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == DELETE_RECIPE) {
                    val recipeId = it.data?.getIntExtra("recipeId",0) ?:0
                    deleteRecipe(recipeId)
                }
            }

        myRecipeViewModel.errorState.observe(viewLifecycleOwner) { resId ->
            showToast(requireContext().getString(resId))
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

    private fun initAdapter() {
        // 최애레시피 초기화
        myRecipeFavoriteAdapter = MyRecipeFavoriteAdapter({ recipeId ->
            val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
            intent.putExtra("recipeId", recipeId)
            startActivity(intent)
        }, { recipeId ->
            deleteFavorite(recipeId)
        })
        binding.myRecipeRvFavoriteRecipe.adapter = myRecipeFavoriteAdapter

        // 내가 쓴 글 초기화
        myRecipeViewModel.foodType.observe(viewLifecycleOwner){
            myRecipeListAdapter = ResearchListAdapter({ recipeId ->
                val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra("recipeId", recipeId)
                detailLauncher.launch(intent)
            }, { _, _ -> }, { recipeId ->
                deleteBookMark(recipeId)
            }, {
                postFavorite(it)
            }, it)
            binding.myRecipeRvPost.adapter = myRecipeListAdapter
        }
    }

    private fun startForFilter() {
        filterLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                //필터 적용 화면에서 돌아왔을때
                when (it.resultCode) {
                    FILTER_APPLY -> {
                        isFilterSet = true
                        minPrice = it.data?.getStringExtra(MIN_KEY)
                        maxPrice = it.data?.getStringExtra(MAX_KEY)
                        category = it.data?.getStringExtra(CATEGORY_KEY)
                        status = it.data?.getStringExtra(STATUS_KEY)
                        flavors = it.data?.getStringArrayListExtra(TASTE_KEY) as ArrayList<String>
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
            intent.putExtra(MIN_KEY, minPrice)
            intent.putExtra(MAX_KEY, maxPrice)
            intent.putExtra(CATEGORY_KEY, category)
            intent.putExtra(STATUS_KEY, status)
            intent.putStringArrayListExtra(TASTE_KEY, flavors)
            filterLauncher.launch(intent)
        }
    }

    private fun getMyPostWithFilter() {
        val categoryName = if (category.isNullOrBlank()) {
            myRecipeViewModel.mode.value?.let { requireContext().resources.getString(it) }
        } else {
            category
        }
        // 필터 아이콘 색깔 적용
        if (isFilterSet) {
            binding.myRecipeIbFilter.setImageResource(R.drawable.ic_filter_green)
        } else {
            binding.myRecipeIbFilter.setImageResource(R.drawable.ic_filter_gray)
        }

        myRecipeViewModel.foodType.observe(viewLifecycleOwner) { foodType ->
            myRecipeViewModel.getMyRecipe(
                categoryName.toString(),
                foodType,
                flavors,
                minPrice,
                maxPrice,
                status,
                sort,
                order)
        }
    }

    private fun getMyFavorite() {
        myRecipeViewModel.getFavoriteRecipe(requireContext().getString(this.mode))
    }

    private fun postFavorite(it: RecipeModel) {
        lifecycleScope.launch {
            myRecipeViewModel.postFavorite(it.id, requireContext().getString(mode))
            getMyPostWithFilter()
        }
    }

    private fun deleteFavorite(recipeId : Int){
        lifecycleScope.launch {
            myRecipeViewModel.deleteFavorite(recipeId)
            getMyFavorite()
            getMyPostWithFilter()
        }
    }

    private fun deleteBookMark(recipeId: Int){
        lifecycleScope.launch {
            myRecipeViewModel.deleteBookMark(recipeId)
            getMyPostWithFilter()
        }
    }

    private fun deleteRecipe(recipeId: Int){
        lifecycleScope.launch{
            myRecipeViewModel.deleteRecipe(recipeId)
            getMyPostWithFilter()
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