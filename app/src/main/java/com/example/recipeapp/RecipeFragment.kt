package com.example.recipeapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.data.APP_RECIPES
import com.example.recipeapp.data.ARG_RECIPE
import com.example.recipeapp.data.Ingredient
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.STUB_RECIPES
import com.example.recipeapp.databinding.FragmentRecipeBinding
import java.io.IOException
import java.io.InputStream

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var recipe: Recipe? = null

    var sP = saveFavorites(STUB_RECIPES.burgerRecipes.map { it.id }.toSet())


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
        binding.ibFavorite.setBackgroundResource(R.drawable.ic_heart_empty)
        var flagFavorite = false

        try {
            val inputStream: InputStream? =
                recipeImageUrl?.let { this.context?.assets?.open(it) }
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.ivRecipe.setImageDrawable(drawable)
        } catch (ex: IOException) {
            Log.e(this.javaClass.simpleName, ex.stackTraceToString())
            return
        }

        binding.ibFavorite.setOnClickListener {
            if (flagFavorite) {
                flagFavorite = false
                it.setBackgroundResource(R.drawable.ic_heart_empty)
            } else {
                flagFavorite = true
                it.setBackgroundResource(R.drawable.ic_heart)
            }
        }

        initRecycler()
    }

    private fun initRecycler() {
        val ingredientsAdapter = recipeIngredients?.let { IngredientsAdapter(it, this) }
        val methodAdapter = recipeMethod?.let { MethodAdapter(it, this) }

        val recyclerIngredientsView: RecyclerView = binding.rvIngredients
        context?.let { it ->
            it.getColor(R.color.line_list_color)
                ?.let { RecyclerViewItemDecoration(it) }
        }
            ?.let { recyclerIngredientsView.addItemDecoration(it) }

        val recyclerMethodView: RecyclerView = binding.rvMethod
        context?.let { it ->
            it.getColor(R.color.line_list_color)
                ?.let { RecyclerViewItemDecoration(it) }
        }
            ?.let { recyclerMethodView.addItemDecoration(it) }

        recyclerIngredientsView.adapter = ingredientsAdapter
        recyclerMethodView.adapter = methodAdapter

        binding.sbPortion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvPortion.text = progress.toString()
                if (ingredientsAdapter != null) {
                    ingredientsAdapter.updateIngredients(progress)
                    recyclerIngredientsView.adapter = ingredientsAdapter
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
    private fun saveFavorites(setOfId: Set<Int>): Any {
        var sharedPrefs: SharedPreferences? = context?.getSharedPreferences(APP_RECIPES, Context.MODE_PRIVATE)

        return 1
    }
}