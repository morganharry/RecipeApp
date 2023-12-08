package com.example.recipeapp.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.STUB_RECIPES
import com.example.recipeapp.databinding.FragmentListRecipesBinding
import com.example.recipeapp.model.ARG_CATEGORY_ID
import com.example.recipeapp.model.ARG_CATEGORY_IMAGE_URL
import com.example.recipeapp.model.ARG_CATEGORY_NAME
import com.example.recipeapp.model.ARG_RECIPE
import com.example.recipeapp.model.Recipe

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {
    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListRecipesBinding must not be null")

    private val listRecipes = STUB_RECIPES.burgerRecipes

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            categoryId = it.getInt(ARG_CATEGORY_ID)
            categoryName = it.getString(ARG_CATEGORY_NAME)
            categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL)
        }

        _binding = FragmentListRecipesBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCategory.text = categoryName

        initRecycler()
    }

    private fun initRecycler() {
        val recipesAdapter = RecipesListAdapter(listRecipes, this)
        val recyclerView: RecyclerView = binding.rvRecipes
        recyclerView.adapter = recipesAdapter

        recipesAdapter.setOnItemClickListener(object :
            RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe: Recipe? = listRecipes.find { it.id == recipeId }
        val bundle = bundleOf(
            ARG_RECIPE to recipe,
        )

        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}