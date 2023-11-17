package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentListRecipesBinding

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {
    private var recipeId: Int? = null
    private var recipeName: String? = null
    private var recipeImageUrl: String? = null

    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListRecipesBinding must not be null")

    private val listRecipes = Recipes.STUB_RECIPES.burgerRecipes

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListRecipesBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            recipeId = it.getInt(ARG_RECIPE_ID)
            recipeName = it.getString(ARG_RECIPE_NAME)
            recipeImageUrl = it.getString(ARG_RECIPE_IMAGE_URL)
        }
        initRecycler()
    }

    private fun initRecycler() {
        val recipesAdapter = RecipesListAdapter(listRecipes, this)
        val recyclerView: RecyclerView = binding.rvBurgerRecipes
        recyclerView.adapter = recipesAdapter

        recipesAdapter.setOnItemClickListener(object :
            RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipeName = listRecipes.find { it.id == recipeId }?.title
        val recipeImageUrl = listRecipes.find { it.id == recipeId }?.imageUrl
        val bundle = bundleOf(
            ARG_RECIPE_ID to recipeId,
            ARG_RECIPE_NAME to recipeName,
            ARG_RECIPE_IMAGE_URL to recipeImageUrl,
        )


        parentFragmentManager.commit {
            add<RecipesListFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack("name")
        }
    }
}
