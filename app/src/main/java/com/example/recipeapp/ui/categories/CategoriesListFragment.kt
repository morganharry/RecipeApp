package com.example.recipeapp.ui.categories

import android.os.Bundle
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
import com.example.recipeapp.ui.recipes.recipeslist.RecipesListFragment

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private val viewModel: CategoriesListViewModel by viewModels()
    private var categoriesAdapter = CategoriesListAdapter(this)

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
        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
        )

        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.nav_host_fragment, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}