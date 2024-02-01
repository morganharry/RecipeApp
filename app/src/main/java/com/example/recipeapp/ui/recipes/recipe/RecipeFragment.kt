package com.example.recipeapp.ui.recipes.recipe

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private val args: RecipeFragmentArgs by navArgs()
    private val viewModel: RecipeViewModel by viewModels()
    private var recipeId: Int? = null
    private var recipeTitle: String? = null
    private var portionsCount: Int = 1
    private var ingredientsAdapter = IngredientsAdapter()
    private var methodAdapter = MethodAdapter()

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
        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeId = args.recipeId
        recipeId?.let {
            viewModel.loadRecipe(it)
        }

        initUI()
    }

    private fun initUI() {
        binding.sbPortion.setOnSeekBarChangeListener(PortionSeekBarListener {
            viewModel.onChangePortions(it)
            binding.tvPortion.text = it.toString()
        })

        val recyclerIngredientsView: RecyclerView = binding.rvIngredients
        context?.getColor(R.color.line_list_color)
            ?.let { RecyclerViewItemDecoration(it) }
            ?.let { recyclerIngredientsView.addItemDecoration(it) }

        val recyclerMethodView: RecyclerView = binding.rvMethod
        context?.getColor(R.color.line_list_color)
            ?.let { RecyclerViewItemDecoration(it) }
            ?.let { recyclerMethodView.addItemDecoration(it) }

        viewModel.recipeLiveData.observe(viewLifecycleOwner) {
            recipeTitle = it.recipe?.title
            binding.tvRecipe.text = recipeTitle

            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.ivRecipe)



            portionsCount = it.portionsCount

            ingredientsAdapter.dataSet = it.recipe?.ingredients ?: listOf()
            recyclerIngredientsView.adapter = ingredientsAdapter
            ingredientsAdapter.updateIngredients(portionsCount)

            methodAdapter.dataSet = it.recipe?.method ?: listOf()
            recyclerMethodView.adapter = methodAdapter

            binding.ibFavorite.apply {
                if (it.isFavorite) {
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
}

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