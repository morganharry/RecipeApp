package com.example.recipeapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.data.ARG_RECIPE
import com.example.recipeapp.data.Ingredient
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var recipe: Recipe? = null

    private var recipeTitle: String? = null
    private var recipeIngredients: List<Ingredient>? = null
    private var recipeMethod: List<String>? = null
    private var recipeImageUrl: String? = null

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }

        recipeTitle = recipe?.title
        recipeIngredients = recipe?.ingredients
        recipeMethod = recipe?.method
        recipeImageUrl = recipe?.imageUrl

        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRecipe.text = recipeTitle

        initRecycler()
    }

    private fun initRecycler() {
        val ingredientsAdapter = recipeIngredients?.let { IngredientsAdapter(it, this) }
        val methodAdapter = recipeMethod?.let { MethodAdapter(it, this) }

        val recyclerIngredientsView: RecyclerView = binding.rvIngredients

        getContext()?.let { RecyclerViewItemDecoration(it, R.drawable.shape_divider) }
            ?.let { recyclerIngredientsView.addItemDecoration(it) }

        val recyclerMethodView: RecyclerView = binding.rvMethod

        getContext()?.let { RecyclerViewItemDecoration(it, R.drawable.shape_divider) }
            ?.let { recyclerMethodView.addItemDecoration(it) }

        recyclerIngredientsView.adapter = ingredientsAdapter
        recyclerMethodView.adapter = methodAdapter
    }
}