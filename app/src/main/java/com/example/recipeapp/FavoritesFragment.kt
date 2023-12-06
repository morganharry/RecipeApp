package com.example.recipeapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.data.APP_RECIPES
import com.example.recipeapp.data.APP_RECIPES_SET_STRING
import com.example.recipeapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentFavoritesBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTest.text = getFavorites()?.first() ?: null

    }
    private fun getFavorites(): MutableSet<String>? {
        val sharedPrefs = context?.getSharedPreferences(APP_RECIPES, Context.MODE_PRIVATE)
        val setOfId = sharedPrefs?.getStringSet(APP_RECIPES_SET_STRING, null)
        return setOfId
    }
}