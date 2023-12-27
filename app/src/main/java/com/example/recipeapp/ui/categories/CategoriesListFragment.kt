package com.example.recipeapp.ui.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.model.ARG_CATEGORY_ID
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import com.example.recipeapp.model.Category
import com.example.recipeapp.ui.recipes.recipeslist.RecipesListFragment

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private val viewModel: CategoriesListViewModel by viewModels()
    private var categoriesList: List<Category> = listOf()
    private var categoriesAdapter = CategoriesListAdapter(categoriesList, this)

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
    }

    private fun initObserver() {
        viewModel.categoriesListLiveData.observe(viewLifecycleOwner) {
            Log.i("categorieslistvm", "${true}")
            initUI(it)
        }
    }

    private fun initUI(categoriesListState: CategoriesListState) {
        categoriesList = categoriesListState?.categoriesList ?: listOf()
        categoriesAdapter = CategoriesListAdapter(categoriesList, this)
        val recyclerView: RecyclerView = binding.rvFavorites
        recyclerView.adapter = categoriesAdapter

        categoriesAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
        )

        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}