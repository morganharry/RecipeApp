package com.example.recipeapp

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {
    @GET("category")
    fun getCategories(): Call<List<Category>>

    @GET("category/{id}")
    fun getCategory(@Path("id") id: Int?): Call<Category>

    @GET("category/{id}/recipes")
    fun getRecipesByCategory(@Path("id") id: Int?): Call<List<Recipe>>

    @GET("recipe/{id}")
    fun getRecipe(@Path("id") id: Int?): Call<Recipe>

    @GET("recipes")
    fun getRecipes(@Query("ids") sort: String?): Call<List<Recipe>>
}