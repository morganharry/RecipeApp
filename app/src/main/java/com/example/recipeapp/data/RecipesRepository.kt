package com.example.recipeapp.data

import com.example.recipeapp.RecipeApiService
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class RecipesRepository {

    private fun createService(): RecipeApiService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://recipes.androidsprint.ru/api/")
            .client(client)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        return retrofit.create(RecipeApiService::class.java)
    }

    fun getCategories(): List<Category>? {
        return try {
            val service = createService()

            val categoriesCall: Call<List<Category>> = service.getCategories()
            val categoriesResponse: Response<List<Category>> = categoriesCall.execute()
            categoriesResponse.body()
        } catch (e: Exception) {
            null
        }
    }


    fun getCategory(id: Int): Category? {
        return try {
            val service = createService()

            val categoryCall: Call<Category> = service.getCategory(id)
            val categoryResponse: Response<Category> = categoryCall.execute()
            categoryResponse.body()
        } catch (e: Exception) {
            null
        }
    }

    fun getRecipesByCategory(id: Int): List<Recipe>? {
        return try {
            val service = createService()

            val recipesCall: Call<List<Recipe>> = service.getRecipesByCategory(id)
            val recipesResponse: Response<List<Recipe>> = recipesCall.execute()
            recipesResponse.body()
        } catch (e: Exception) {
            null
        }
    }

    fun getRecipe(id: Int): Recipe? {
        return try {
            val service = createService()

            val recipeCall: Call<Recipe> = service.getRecipe(id)
            val recipeResponse: Response<Recipe> = recipeCall.execute()
            recipeResponse.body()
        } catch (e: Exception) {
            null
        }
    }

    fun getRecipes(ids: String): List<Recipe>? {
        return try {
            val service = createService()

            val recipesCall: Call<List<Recipe>> = service.getRecipes(ids)
            val recipesResponse: Response<List<Recipe>> = recipesCall.execute()
            recipesResponse.body()
        } catch (e: Exception) {
            null
        }
    }
}