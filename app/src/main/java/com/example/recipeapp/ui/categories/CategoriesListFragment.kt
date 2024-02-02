package com.example.recipeapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private val viewModel: CategoriesListViewModel by viewModels()
    private var categoriesAdapter = CategoriesListAdapter()

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

        initUI()
    }

    private fun initUI() {
        val recyclerView: RecyclerView = binding.rvCategories
        categoriesAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })

        viewModel.categoriesListLiveData.observe(viewLifecycleOwner) {
            categoriesAdapter.dataSet = it.categoriesList ?: listOf()
            recyclerView.adapter = categoriesAdapter
        }
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        findNavController().navigate(
            CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
                categoryId
            )
        )
    }
}