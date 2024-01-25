package com.example.recipeapp.data

import com.example.recipeapp.RecipeApiService
import com.example.recipeapp.model.Category
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class RecipesRepository {

    val contentType = "application/json".toMediaType()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://recipes.androidsprint.ru/api/")
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    val categoriesCall: Call<List<Category>> = service.getCategories()
    val categoriesResponse: Response<List<Category>> = categoriesCall.execute()
    val categories: List<Category>? = categoriesResponse.body()








}