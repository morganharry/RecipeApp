package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.content.Context
import android.util.Log
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
    private val repository by lazy { RecipesRepository(application) }
    private var recipesList: List<Recipe>? = listOf()

    val favoritesLiveData: LiveData<FavoritesState>
        get() = _favoritesLiveData
    private val _favoritesLiveData = MutableLiveData<FavoritesState>()

    init {
        Log.i("VM", "FavoritesViewVM created")
    }

    fun loadRecipesList() {
        val thread = Thread {
            val favList = getFavorites()
            recipesList = repository.getRecipes(favList.joinToString(","))
            _favoritesLiveData.postValue(FavoritesState(recipesList))

            Log.i("!!!", "favList: ${recipesList.toString()}")
        }

        thread.start()
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