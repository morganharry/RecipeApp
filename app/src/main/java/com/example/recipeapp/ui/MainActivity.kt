package com.example.recipeapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.model.Category
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private val threadPool = Executors.newFixedThreadPool(10)
    private var categories: List<Category> = listOf()
    private var categoriesID: List<Int> = listOf()

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        val thread = Thread {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val requestCategories: Request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/category")
                .header("User-Agent", "OkHttp Example")
                .build()

            client.newCall(requestCategories).execute().use { response ->
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                val categoriesString = response.body?.string()

                categories = categoriesString?.let { Json.decodeFromString(it) }
                    ?: throw IllegalStateException("Categories not found.")
                categoriesID = categories.map { it.id }

                categoriesID.forEach {
                    val threadRecipe = Runnable {
                        val categoryID = it.toString()
                        val requestRecipes: Request = Request.Builder()
                            .url("https://recipes.androidsprint.ru/api/category/$categoryID/recipes")
                            .build()

                        client.newCall(requestRecipes).execute().use { response ->
                            Log.i(
                                "!!!",
                                "Выполняю запрос на потоке: ${Thread.currentThread().name}"
                            )

                            val recipesString = response.body?.string()
                        }
                    }
                    threadPool.execute(threadRecipe)
                }
            }
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