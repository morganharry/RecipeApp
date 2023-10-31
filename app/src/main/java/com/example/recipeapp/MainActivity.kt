package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnContextClickListener
import android.widget.Toast
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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
                supportFragmentManager.commit {
                    replace<CategoriesListFragment>(R.id.mainContainer)
                    setReorderingAllowed(true)
                    addToBackStack("name")
                }
            }
            buttonFavorites.setOnClickListener {
                supportFragmentManager.commit {
                    replace<FavoritesFragment>(R.id.mainContainer)
                    setReorderingAllowed(true)
                    addToBackStack("name")
                }
            }
        }
    }
}
