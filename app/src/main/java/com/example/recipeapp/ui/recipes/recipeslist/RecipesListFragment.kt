package com.example.recipeapp.ui.recipes.recipeslist

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentListRecipesBinding
import com.example.recipeapp.model.ARG_CATEGORY_ID

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {
    private val viewModel: RecipesListViewModel by viewModels()
    private var categoryId: Int? = null
    private var categoryTitle: String? = null
    private var categoryImageDrawable: Drawable? = null
    private var recipesAdapter = RecipesListAdapter(this)

    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListRecipesBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        categoryId = arguments?.getInt(ARG_CATEGORY_ID)
        categoryId?.let {
            viewModel.loadRecipesList(it)
        }

        _binding = FragmentListRecipesBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        val recyclerView: RecyclerView = binding.rvRecipes

        recipesAdapter.setOnItemClickListener(object :
            RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })

        viewModel.recipesListData.observe(viewLifecycleOwner) {
            categoryTitle = it.categoryTitle
            binding.tvCategory.text = categoryTitle
            categoryImageDrawable = it.categoryDrawable
            binding.ivCategory.setImageDrawable(categoryImageDrawable)

            recipesAdapter.dataSet = it.recipesList ?: listOf()
            recyclerView.adapter = recipesAdapter
        }
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        findNavController().navigate(
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(
                recipeId
            )
        )
    }
}