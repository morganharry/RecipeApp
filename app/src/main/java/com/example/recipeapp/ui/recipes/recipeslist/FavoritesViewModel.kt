package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.APP_RECIPES
import com.example.recipeapp.model.APP_RECIPES_SET_STRING
import com.example.recipeapp.model.Recipe

data class FavoritesState(
    var recipesList: List<Recipe>? = null,
)

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    val favoritesData: LiveData<FavoritesState>
        get() = _favoritesData
    private val _favoritesData = MutableLiveData<FavoritesState>()

    init {
        Log.i("favoritesvm", "VM created")
    }

    fun loadRecipesList() {
        val favList = getFavorites()
        val recipesList: List<Recipe> = STUB.getRecipesByIds(favList)

        _favoritesData.value = FavoritesState(recipesList)
    }

    private fun getFavorites(): HashSet<String> {
        val sharedPrefs = application.getSharedPreferences(APP_RECIPES, Context.MODE_PRIVATE)
        val fav: Set<String> =
            sharedPrefs.getStringSet(APP_RECIPES_SET_STRING, emptySet()) ?: emptySet()
        return HashSet(fav)
    }

    override fun onCleared() {
        Log.i("favoritesvm", "VM cleared")
        super.onCleared()
    }
}