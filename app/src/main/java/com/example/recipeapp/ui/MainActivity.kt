package com.example.recipeapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.RecipeApiService
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.model.Category
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val thread = Thread {
            val contentType = "application/json".toMediaType()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://recipes.androidsprint.ru/api/")
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()

            val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

            val categoriesCall: Call<List<Category>> = service.getCategories()
            val categoriesResponse: Response<List<Category>> = categoriesCall.execute()
            val categories: List<Category>? = categoriesResponse.body()
            Log.i("!!!", "categories: ${categories.toString()}")

        }

        thread.start()

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
