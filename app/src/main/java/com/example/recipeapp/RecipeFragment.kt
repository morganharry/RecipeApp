package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.data.ARG_CATEGORY_ID
import com.example.recipeapp.data.ARG_CATEGORY_IMAGE_URL
import com.example.recipeapp.data.ARG_CATEGORY_NAME
import com.example.recipeapp.data.ARG_RECIPE
import com.example.recipeapp.data.Ingredient
import com.example.recipeapp.data.STUB_RECIPES
import com.example.recipeapp.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var recipeId: Int? = null
    private var recipeTitle: String? = null
    private var recipeIngredients: List<Ingredient>? = null
    private var recipeMethod: List<String>? = null
    private var recipeImageUrl: String? = null

    private val listRecipes = STUB_RECIPES.burgerRecipes

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            recipeId = it.getInt(ARG_RECIPE)
        }
        recipeTitle = listRecipes.find { it.id == recipeId }?.title
        recipeIngredients  = listRecipes.find { it.id == recipeId }?.ingredients
        recipeMethod = listRecipes.find { it.id == recipeId }?.method
        recipeImageUrl = listRecipes.find { it.id == recipeId }?.imageUrl

        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitleRecipe.text = recipeTitle
    }
}