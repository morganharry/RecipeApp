package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.APP_RECIPES
import com.example.recipeapp.model.APP_RECIPES_SET_STRING
import com.example.recipeapp.model.Recipe

data class FavoritesState(
    var recipesList: List<Recipe>? = null,
)

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository by lazy { RecipesRepository() }
    private var recipesList: List<Recipe>? = listOf()

    val favoritesLiveData: LiveData<FavoritesState>
        get() = _favoritesLiveData
    private val _favoritesLiveData = MutableLiveData<FavoritesState>()

    init {
        Log.i("VM", "FavoritesViewVM created")
    }

    fun loadRecipesList() {
        Thread {
            val favList = getFavorites()
            if (favList.isNotEmpty()) {
                recipesList = repository.getRecipes(favList.joinToString(","))
                if (recipesList == null) {
                    val text = "Ошибка получения данных"
                    val duration = Toast.LENGTH_LONG
                    Toast.makeText(application, text, duration).show()
                }
            } else recipesList = listOf()

            _favoritesLiveData.postValue(FavoritesState(recipesList))

            Log.i("!!!", "favList: ${recipesList.toString()}")
        }.start()
    }

    private fun getFavorites(): HashSet<String> {
        val sharedPrefs = application.getSharedPreferences(APP_RECIPES, Context.MODE_PRIVATE)
        val fav: Set<String> =
            sharedPrefs.getStringSet(APP_RECIPES_SET_STRING, emptySet()) ?: emptySet()
        return HashSet(fav)
    }

    override fun onCleared() {
        Log.i("VM", "FavoritesViewVM cleared")
        super.onCleared()
    }
}