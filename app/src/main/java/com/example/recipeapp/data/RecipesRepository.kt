package com.example.recipeapp.data

import android.app.Application
import android.widget.Toast
import com.example.recipeapp.RecipeApiService
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

fun showError(application: Application) {
    val text = "Ошибка получения данных"
    val duration = Toast.LENGTH_LONG
    Toast.makeText(application, text, duration).show()
}

class RecipesRepository(private val application: Application) {

    private fun createService(): RecipeApiService {
        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://recipes.androidsprint.ru/api/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        return retrofit.create(RecipeApiService::class.java)
    }


    fun getCategories(): List<Category>? {
        val service = createService()

        val categoriesCall: Call<List<Category>> = service.getCategories()
        val categoriesResponse: Response<List<Category>> = categoriesCall.execute()
        if (categoriesResponse.body() == null) showError(application)
        return categoriesResponse.body()
    }

    fun getCategory(id: Int): Category? {
        val service = createService()

        val categoryCall: Call<Category> = service.getCategory(id)
        val categoryResponse: Response<Category> = categoryCall.execute()
        if (categoryResponse.body() == null) showError(application)
        return categoryResponse.body()
    }

    fun getRecipesByCategory(id: Int): List<Recipe>? {
        val service = createService()

        val recipesCall: Call<List<Recipe>> = service.getRecipesByCategory(id)
        val recipesResponse: Response<List<Recipe>> = recipesCall.execute()
        recipesResponse.body()
        if (recipesResponse.body() == null) showError(application)
        return recipesResponse.body()
    }

    fun getRecipe(id: Int): Recipe? {
        val service = createService()

        val recipeCall: Call<Recipe> = service.getRecipe(id)
        val recipeResponse: Response<Recipe> = recipeCall.execute()
        if (recipeResponse.body() == null) showError(application)
        return recipeResponse.body()
    }

    fun getRecipes(ids: String): List<Recipe>? {
        val service = createService()

        val recipesCall: Call<List<Recipe>> = service.getRecipes(ids)
        val recipesResponse: Response<List<Recipe>> = recipesCall.execute()
        if (recipesResponse.body() == null) showError(application)
        return recipesResponse.body()
    }
}