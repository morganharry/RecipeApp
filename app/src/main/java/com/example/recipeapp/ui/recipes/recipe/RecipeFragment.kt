package com.example.recipeapp.ui.recipes.recipe

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
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.model.ARG_RECIPE_ID
import com.example.recipeapp.model.Ingredient
import java.io.IOException
import java.io.InputStream

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private val viewModel: RecipeViewModel by viewModels()
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
        recipeId = arguments?.getInt(ARG_RECIPE_ID)
        recipeId?.let { viewModel.loadRecipe(it) }

        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        viewModel.recipeLiveData.observe(viewLifecycleOwner) {
            Log.i("recipevm", "${it.isFavorite}")
            initUI(viewModel.recipeLiveData)
            initRecycler(viewModel.recipeLiveData)
        }
    }

    private fun initRecycler(recipeState: LiveData<RecipeState>) {
        recipeIngredients = recipeState.value?.recipe?.ingredients
        recipeMethod = recipeState.value?.recipe?.method

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
                ingredientsAdapter?.updateIngredients(progress)
                recyclerIngredientsView.adapter = ingredientsAdapter
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun initUI(recipeState: LiveData<RecipeState>) {
        recipeTitle = recipeState.value?.recipe?.title
        recipeImageUrl = recipeState.value?.recipe?.imageUrl

        binding.tvRecipe.text = recipeTitle

        try {
            val inputStream: InputStream? = recipeImageUrl?.let { this.context?.assets?.open(it) }
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.ivRecipe.setImageDrawable(drawable)
        } catch (ex: IOException) {
            Log.e(this.javaClass.simpleName, ex.stackTraceToString())
            return
        }

        binding.ibFavorite.apply {

            if (recipeState.value?.isFavorite == true) {
                setBackgroundResource(R.drawable.ic_heart)
            } else {
                setBackgroundResource(R.drawable.ic_heart_empty)
            }

            setOnClickListener {
                recipeId?.let { it1 -> viewModel.onFavoritesClicked(it1) }
            }
        }
    }
}