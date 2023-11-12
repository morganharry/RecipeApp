package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")

    private val listCategories = mutableListOf<Category>(
        Category(0, "Бургеры", "Рецепты всех популярных видов бургеров", "burger.png"),
        Category(1, "Десерты", "Самые вкусные рецепты десертов специально для вас", "dessert.png"),
        Category(2, "Пицца", "Пицца на любой вкус и цвет. Лучшая подборка для тебя", "pizza.png"),
        Category(3, "Рыба", "Печеная, жареная, сушеная, любая рыба на твой вкус", "fish.png"),
        Category(4, "Супы", "От классики до экзотики: мир в одной тарелке", "soup.png"),
        Category(5, "Салаты", "Хрустящий калейдоскоп под соусом вдохновения", "salad.png"),
    )

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
        val recyclerView: RecyclerView = binding.rvCategories
        recyclerView.adapter = categoriesAdapter

        categoriesAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            openRecipesByCategoryId()
        })
    }

    private fun openRecipesByCategoryId() {
        override fun onItemClick() {
            supportFragmentManager.commit {
                replace<RecipesListFragment>(R.id.mainContainer)
                setReorderingAllowed(true)
                addToBackStack("name")
            }
        }
    }
}