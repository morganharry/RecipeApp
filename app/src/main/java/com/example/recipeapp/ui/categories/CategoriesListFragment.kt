package com.example.recipeapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.ARG_CATEGORY_ID
import com.example.recipeapp.model.ARG_CATEGORY_IMAGE_URL
import com.example.recipeapp.model.ARG_CATEGORY_NAME
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import com.example.recipeapp.ui.recipes.recipeslist.RecipesListFragment

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")

    private val listCategories = STUB.getCategories()

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

        initRecycler()
    }

    private fun initRecycler() {
        val categoriesAdapter = CategoriesListAdapter(listCategories, this)
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
        val categoryName = listCategories.find { it.id == categoryId }?.title
        val categoryImageUrl = listCategories.find { it.id == categoryId }?.imageUrl
        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
            ARG_CATEGORY_NAME to categoryName,
            ARG_CATEGORY_IMAGE_URL to categoryImageUrl,
        )

        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}