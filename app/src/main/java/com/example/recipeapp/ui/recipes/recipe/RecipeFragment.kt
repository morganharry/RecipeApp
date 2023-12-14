package com.example.recipeapp.ui.recipes.recipe

import android.content.Context
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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.model.APP_RECIPES
import com.example.recipeapp.model.APP_RECIPES_SET_STRING
import com.example.recipeapp.model.ARG_RECIPE
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.Recipe
import java.io.IOException
import java.io.InputStream

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private val viewModel: RecipeViewModel by viewModels()

    private var recipe: Recipe? = null
    private var recipeId: Int? = null
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

        recipeId = recipe?.id
        recipeTitle = recipe?.title
        recipeIngredients = recipe?.ingredients
        recipeMethod = recipe?.method
        recipeImageUrl = recipe?.imageUrl

        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            val inputStream: InputStream? = recipeImageUrl?.let { this.context?.assets?.open(it) }
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.ivRecipe.setImageDrawable(drawable)
        } catch (ex: IOException) {
            Log.e(this.javaClass.simpleName, ex.stackTraceToString())
            return
        }

        binding.ibFavorite.apply {
            if (getFavorites().contains(recipeId.toString())) {
                setBackgroundResource(R.drawable.ic_heart)
            } else {
                setBackgroundResource(R.drawable.ic_heart_empty)
            }

            setOnClickListener {
                if (getFavorites().contains(recipeId.toString())) {
                    it.setBackgroundResource(R.drawable.ic_heart_empty)
                } else {
                    it.setBackgroundResource(R.drawable.ic_heart)
                }

                val fav = getFavorites()

                if (fav.contains(recipeId.toString())) {
                    fav.remove(recipeId.toString())
                } else {
                    fav.add(recipeId.toString())
                }
                saveFavorites(fav)
            }
        }

        initRecycler()

        viewModel.recipeLiveData.observe(viewLifecycleOwner) {
            Log.i("recipevm", "${it.isFavorite}")
        }
    }

    private fun initRecycler() {
        val ingredientsAdapter = recipeIngredients?.let { IngredientsAdapter(it, this) }
        val methodAdapter = recipeMethod?.let { MethodAdapter(it, this) }
        val recyclerIngredientsView: RecyclerView = binding.rvIngredients

        context?.getColor(R.color.line_list_color)
            ?.let { RecyclerViewItemDecoration(it) }
            ?.let { recyclerIngredientsView.addItemDecoration(it) }

        val recyclerMethodView: RecyclerView = binding.rvMethod
        context?.getColor(R.color.line_list_color)
            ?.let { RecyclerViewItemDecoration(it) }
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

    private fun saveFavorites(setOfId: Set<String>) {
        context?.getSharedPreferences(APP_RECIPES, Context.MODE_PRIVATE)
            ?.edit()
            ?.putStringSet(APP_RECIPES_SET_STRING, setOfId)
            ?.apply()
    }

    private fun getFavorites(): HashSet<String> {
        val sharedPrefs = requireContext().getSharedPreferences(APP_RECIPES, Context.MODE_PRIVATE)
        val fav: Set<String> =
            sharedPrefs.getStringSet(APP_RECIPES_SET_STRING, emptySet()) ?: emptySet()
        return HashSet(fav)
    }
}