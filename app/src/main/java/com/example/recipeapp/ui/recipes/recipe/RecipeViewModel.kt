package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.APP_RECIPES
import com.example.recipeapp.model.APP_RECIPES_SET_STRING
import com.example.recipeapp.model.Recipe
import java.io.InputStream

data class RecipeState(
    var recipe: Recipe? = null,
    var recipeDrawable: Drawable? = null,
    var portionsCount: Int = 1,
    var isFavorite: Boolean = false,
)

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository by lazy { RecipesRepository() }
    private var recipe: Recipe? = null
    val recipeLiveData: LiveData<RecipeState>
        get() = _recipeLiveData
    private val _recipeLiveData = MutableLiveData<RecipeState>()

    init {
        Log.i("VM", "RecipeViewVM created")
    }

    fun loadRecipe(recipeId: Int) {
        val thread = Thread {
            recipe = repository.getRecipe(recipeId)

            val recipeDrawable: Drawable? = try {
                val inputStream: InputStream? =
                    recipe?.imageUrl.let { it?.let { it1 -> this.application.assets?.open(it1) } }
                Drawable.createFromStream(inputStream, null)
            } catch (ex: Exception) {
                Log.e(this.javaClass.simpleName, ex.stackTraceToString())
                null
            }

            val portionsCount: Int = _recipeLiveData.value?.portionsCount ?: 1
            val isFavorite = getFavorites().contains(recipe?.id.toString())

            _recipeLiveData.postValue(
                RecipeState(
                    recipe,
                    recipeDrawable,
                    portionsCount,
                    isFavorite,
                )
            )

            Log.i("!!!", "recipe: ${recipe.toString()}")
        }

        thread.start()
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
        Log.i("VM", "RecipeViewVM cleared")
        super.onCleared()
    }

    fun onChangePortions(progress: Int) {
        _recipeLiveData.value = _recipeLiveData.value?.copy(portionsCount = progress)
    }
}