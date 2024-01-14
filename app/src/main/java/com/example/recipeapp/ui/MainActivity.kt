package com.example.recipeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.model.Category
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
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
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()

            Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            Log.i("!!!", "responseCode: ${connection.responseCode}")
            Log.i("!!!", "responseMessage: ${connection.responseMessage}")

            val categoriesString = connection.inputStream.bufferedReader().readText()
            Log.i("!!!", "Body: $categoriesString")
            categories = Json.decodeFromString(categoriesString)
            categoriesID = categories.map { it.id }
        }

        thread.start()

        categoriesID.forEach {
            val threadRecipe = Runnable {
                val categoryID = it.toString()
                val url = URL("https://recipes.androidsprint.ru/api/category/$categoryID/recipes")
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connect()

                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                Log.i("!!!", "responseCode: ${connection.responseCode}")
                Log.i("!!!", "responseMessage: ${connection.responseMessage}")

                val recipesString = connection.inputStream.bufferedReader().readText()
                Log.i("!!!", "Body: $recipesString")
            }
            threadPool.execute(threadRecipe)
        }


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