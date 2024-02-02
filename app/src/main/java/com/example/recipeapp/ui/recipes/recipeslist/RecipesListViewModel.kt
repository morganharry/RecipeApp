package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.URL_IMAGES

data class RecipesListState(
    var categoryTitle: String? = null,
    var categoryImageUrl: String? = null,
    var recipesList: List<Recipe>? = null,
)

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository by lazy { RecipesRepository() }
    private var recipesList: List<Recipe>? = listOf()
    private var category: Category? = null

    val recipesListLiveData: LiveData<RecipesListState>
        get() = _recipesListLiveData
    private val _recipesListLiveData = MutableLiveData<RecipesListState>()

    init {
        Log.i("VM", "RecipesListVM created")
    }

    fun loadRecipesList(categoryId: Int) {
        Thread {
            category = repository.getCategory(categoryId)
            if (category == null) {
                val text = "Ошибка получения данных"
                val duration = Toast.LENGTH_LONG
                Toast.makeText(application, text, duration).show()
            }
            val categoryTitle = category?.title

            val categoryImageUrl =
                "$URL_IMAGES${category?.imageUrl}"

            recipesList = repository.getRecipesByCategory(categoryId)
            _recipesListLiveData.postValue(
                RecipesListState(
                    categoryTitle,
                    categoryImageUrl,
                    recipesList
                )
            )
            Log.i("!!!", "category: ${category.toString()}")
            Log.i("!!!", "recipesList: ${recipesList.toString()}")
        }.start()
    }

    override fun onCleared() {
        Log.i("VM", "RecipesListVM cleared")
        super.onCleared()
    }
}