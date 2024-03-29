package com.example.recipeapp.data

import android.app.Application
import androidx.room.Room
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.URL_API
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class RecipesRepository(application: Application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "database"
    ).fallbackToDestructiveMigration()
        .build()

    private val categoriesDao = db.categoryDao()
    private val recipesDao = db.recipesDao()


    suspend fun insertRecipe(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            recipesDao.insertRecipe(recipe)
        }
    }

    suspend fun getRecipeFromCache(recipeId: Int): Recipe {
        return withContext(Dispatchers.IO) {
            recipesDao.getRecipe(recipeId)
        }
    }


    suspend fun getFavoritesFromCache(): List<Recipe> {
        return withContext(Dispatchers.IO) {
            recipesDao.getFavorites() ?: emptyList()
        }
    }

    suspend fun insertCategories(categories: List<Category>) {
        withContext(Dispatchers.IO) {
            categoriesDao.insert(categories)
        }
    }

    suspend fun insertRecipesListByCategory(recipes: List<Recipe>) {
        withContext(Dispatchers.IO) {
            recipesDao.insert(recipes)
        }
    }

    suspend fun getCategoriesFromCache(): List<Category> {
        return withContext(Dispatchers.IO) {
            categoriesDao.getAll()
        }
    }

    suspend fun getCategoryFromCache(categoryId: Int): Category {
        return withContext(Dispatchers.IO) {
            categoriesDao.loadById(categoryId)
        }
    }

    suspend fun getRecipesListByCategoryFromCache(categoryId: Int): List<Recipe> {
        return withContext(Dispatchers.IO) {
            recipesDao.loadByCategoryId(categoryId)
        }
    }

    private fun createService(): RecipeApiService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL_API)
            .client(client)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        return retrofit.create(RecipeApiService::class.java)
    }

    suspend fun getCategories(): List<Category>? {
        return try {
            val service = createService()

            withContext(Dispatchers.IO) {
                val categoriesCall: Call<List<Category>> = service.getCategories()
                val categoriesResponse: Response<List<Category>> = categoriesCall.execute()
                categoriesResponse.body()
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getRecipesByCategory(id: Int): List<Recipe>? {
        return try {
            val service = createService()

            withContext(Dispatchers.IO) {
                val recipesCall: Call<List<Recipe>> = service.getRecipesByCategory(id)
                val recipesResponse: Response<List<Recipe>> = recipesCall.execute()
                recipesResponse.body()
            }


        } catch (e: Exception) {
            null
        }
    }

    suspend fun getRecipe(id: Int): Recipe? {
        return try {
            val service = createService()

            withContext(Dispatchers.IO) {
                val recipeCall: Call<Recipe> = service.getRecipe(id)
                val recipeResponse: Response<Recipe> = recipeCall.execute()
                recipeResponse.body()
            }
        } catch (e: Exception) {
            null
        }
    }
}