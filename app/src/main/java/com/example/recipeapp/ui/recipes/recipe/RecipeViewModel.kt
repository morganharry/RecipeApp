package com.example.recipeapp.ui.recipes.recipe

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.STUB_RECIPES
import com.example.recipeapp.model.APP_RECIPES
import com.example.recipeapp.model.APP_RECIPES_SET_STRING
import com.example.recipeapp.model.Recipe

data class RecipeState(
    var recipe: Recipe? = null,
    var progressSeekBar: Int = 1,
    var isFavorite: Boolean = getFavorites().contains(recipe?.id.toString()),
)

class RecipeViewModel : ViewModel() {

    val recipeLiveData: LiveData<RecipeState>
        get() = _recipeLiveData
    private val _recipeLiveData = MutableLiveData<RecipeState>()

    init {
        Log.i("recipevm", "VM created")
        _recipeLiveData.value = RecipeState(null, 1, false)
    }

    override fun onCleared() {
        Log.i("recipevm", "VM cleared")
        super.onCleared()
    }

    fun loadRecipe(recipeId: Int) {
        val recipe: Recipe? = STUB_RECIPES.burgerRecipes.find { it.id == recipeId }

        _recipeLiveData.value = RecipeState(recipe)
        TODO("load from network")
    }
    fun getFavorites(): HashSet<String> {
        val sharedPrefs = requireContext()?.getSharedPreferences(APP_RECIPES, Context.MODE_PRIVATE)
        val fav: Set<String> =
            sharedPrefs.getStringSet(APP_RECIPES_SET_STRING, emptySet()) ?: emptySet()
        return HashSet(fav)
    }
}

