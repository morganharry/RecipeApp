package com.example.recipeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonCategories.setOnClickListener {
                findNavController(R.id.nav_host_fragment).navigate(R.id.categoriesListFragment)
            }
            buttonFavorites.setOnClickListener {
                findNavController(R.id.nav_host_fragment).navigate(R.id.favoritesFragment)
            }
        }
    }
}