package com.doctor.yumyum.presentation.ui.myrecipe

import ResearchListAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.DialogMyRecipeSortBinding
import com.doctor.yumyum.databinding.FragmentMyRecipeBinding
import com.doctor.yumyum.presentation.ui.recipedetail.RecipeDetailActivity
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

    private var categoryName = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initDialog()
        initRecycler()
        startForFilter()


        myRecipeViewModel.mode.observe(viewLifecycleOwner) { mode ->
            binding.myRecipeIbMode.setImageResource(
                if (mode == R.string.common_food) R.drawable.ic_change_food else R.drawable.ic_change_beverage
            )
            categoryName = requireContext().resources.getString(mode)
            myRecipeViewModel.foodType.observe(viewLifecycleOwner){ foodType ->
                CoroutineScope(Dispatchers.IO).launch {
                    myRecipeViewModel.getMyRecipe(categoryName,foodType)
                }
            }
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

    private fun initRecycler() {
        val myRecipeListAdapter = ResearchListAdapter({ recipeId ->
            val intent = Intent(requireContext(),RecipeDetailActivity::class.java)
            intent.putExtra("recipeId", recipeId)
            startActivity(intent)
        },{})
        binding.myRecipeRvPost.adapter = myRecipeListAdapter

        myRecipeViewModel.myRecipeList.observe(viewLifecycleOwner){
            myRecipeListAdapter.setRecipeList(it)
        }
    }

    private fun startForFilter() {
        filterLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //TODO : 정렬 정보 가져오기
        }
        binding.myRecipeIbFilter.setOnClickListener {
            val intent = Intent(context, MyPageFilterActivity::class.java)
            filterLauncher.launch(intent)
        }
    }

    fun showBottomSheet() {
        sortSelectDialog.show()
    }
}