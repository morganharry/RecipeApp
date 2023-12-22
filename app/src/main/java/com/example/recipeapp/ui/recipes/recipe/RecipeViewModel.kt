package com.example.recipeapp.ui.recipes.recipe

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

data class RecipeState(
    var recipe: Recipe? = null,
    var portionsCount: Int? = null,
    var isFavorite: Boolean = false,
)

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {

    val recipeLiveData: LiveData<RecipeState>
        get() = _recipeLiveData
    private val _recipeLiveData = MutableLiveData<RecipeState>()

    init {
        Log.i("recipevm", "VM created")
        _recipeLiveData.value = RecipeState(null, null, false)
    }

    fun loadRecipe(recipeId: Int) {
        val recipe: Recipe = STUB.getRecipeById(recipeId)
        val portionsCount: Int = _recipeLiveData.value?.portionsCount ?: 1
        val isFavorite = getFavorites().contains(recipe.id.toString())

        _recipeLiveData.value = RecipeState(recipe, portionsCount, isFavorite)
        //TODO("load from network")
    }

    private fun getFavorites(): HashSet<String> {
        val sharedPrefs = application.getSharedPreferences(APP_RECIPES, Context.MODE_PRIVATE)
        val fav: Set<String> =
            sharedPrefs.getStringSet(APP_RECIPES_SET_STRING, emptySet()) ?: emptySet()
        return HashSet(fav)
    }

    private fun saveFavorites(setOfId: Set<String>) {
        application.getSharedPreferences(APP_RECIPES, Context.MODE_PRIVATE)
            ?.edit()
            ?.putStringSet(APP_RECIPES_SET_STRING, setOfId)
            ?.apply()
    }

    fun onFavoritesClicked(recipeId: Int) {
        val isFavRecipe = getFavorites().contains(recipeId.toString())
        _recipeLiveData.value = _recipeLiveData.value?.copy(isFavorite = !isFavRecipe)

        val favSet = getFavorites()

        if (favSet.contains(recipeId.toString())) {
            favSet.remove(recipeId.toString())
        } else {
            favSet.add(recipeId.toString())
        }

        saveFavorites(favSet)
    }

    override fun onCleared() {
        Log.i("recipevm", "VM cleared")
        super.onCleared()
    }
}