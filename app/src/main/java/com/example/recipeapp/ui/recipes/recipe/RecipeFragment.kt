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
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.model.ARG_RECIPE_ID
import com.example.recipeapp.model.Ingredient

class PortionSeekBarListener(val onChangeIngredients: (Int) -> Unit) :
    SeekBar.OnSeekBarChangeListener {

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onChangeIngredients(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private val viewModel: RecipeViewModel by viewModels()
    private var recipeId: Int? = null
    private var recipeTitle: String? = null
    private var portionsCount: Int = 1
    private var recipeIngredients: List<Ingredient>? = null
    private var recipeMethod: List<String>? = null
    private var recipeImageDrawable: Drawable? = null
    private var ingredientsAdapter = recipeIngredients?.let { IngredientsAdapter(listOf()) }
    private var methodAdapter = recipeMethod?.let { MethodAdapter(listOf()) }
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
            initUI(it)
        }
    }

    private fun initUI(recipeState: RecipeState) {
        recipeTitle = recipeState.recipe?.title
        recipeImageDrawable = recipeState.recipeDrawable
        portionsCount = recipeState.portionsCount
        recipeIngredients = recipeState.recipe?.ingredients
        recipeMethod = recipeState.recipe?.method

        binding.tvRecipe.text = recipeTitle
        binding.ivRecipe.setImageDrawable(recipeImageDrawable)

        ingredientsAdapter = recipeIngredients?.let { IngredientsAdapter(recipeIngredients!!) }
        methodAdapter = recipeMethod?.let { MethodAdapter(recipeMethod!!) }

        val recyclerIngredientsView: RecyclerView = binding.rvIngredients
        context?.getColor(R.color.line_list_color)
            ?.let { RecyclerViewItemDecoration(it) }
            ?.let { recyclerIngredientsView.addItemDecoration(it) }
        recyclerIngredientsView.adapter = ingredientsAdapter

        ingredientsAdapter?.updateIngredients(portionsCount)

        val recyclerMethodView: RecyclerView = binding.rvMethod
        context?.getColor(R.color.line_list_color)
            ?.let { RecyclerViewItemDecoration(it) }
            ?.let { recyclerMethodView.addItemDecoration(it) }
        recyclerMethodView.adapter = methodAdapter

        binding.ibFavorite.apply {
            if (recipeState.isFavorite) {
                setBackgroundResource(R.drawable.ic_heart)
            } else {
                setBackgroundResource(R.drawable.ic_heart_empty)
            }

            setOnClickListener {
                recipeId?.let { it1 -> viewModel.onFavoritesClicked(it1) }
            }
        }

        binding.sbPortion.setOnSeekBarChangeListener(PortionSeekBarListener {
            viewModel.onChangePortions(it)
            binding.tvPortion.text = it.toString()
        })
    }
}