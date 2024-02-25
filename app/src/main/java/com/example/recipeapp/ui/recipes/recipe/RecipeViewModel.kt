package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.URL_IMAGES
import kotlinx.coroutines.launch

data class RecipeState(
    var recipe: Recipe? = null,
    var imageUrl: String? = null,
    var portionsCount: Int = 1,
    var isFavorite: Int? = 0,
)

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository by lazy { RecipesRepository(application) }
    private var recipe: Recipe? = null
    val recipeLiveData: LiveData<RecipeState>
        get() = _recipeLiveData
    private val _recipeLiveData = MutableLiveData<RecipeState>()

    init {
        Log.i("VM", "RecipeViewVM created")
    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            recipe = repository.getRecipeFromCache(recipeId)



            recipe = repository.getRecipe(recipeId)
            if (recipe == null) {
                val text = "Ошибка получения данных"
                val duration = Toast.LENGTH_LONG
                Toast.makeText(application, text, duration).show()
            }

            val imageUrl = "$URL_IMAGES${recipe?.imageUrl}"
            val portionsCount: Int = _recipeLiveData.value?.portionsCount ?: 1
            val isFavorite = recipe?.isFavorite

            _recipeLiveData.postValue(
                RecipeState(
                    recipe,
                    imageUrl,
                    portionsCount,
                    isFavorite,
                )
            )
        }

        Log.i("!!!", "recipe: ${recipe.toString()}")
    }

    fun onFavoritesClicked(recipeId: Int) {
        viewModelScope.launch {
            recipe = repository.getRecipeFromCache(recipeId)

            if (recipe!!.isFavorite == 1) recipe!!.isFavorite = 0
            else recipe!!.isFavorite = 0

            repository.insertRecipe(recipe!!)
        }
    }

    override fun onCleared() {
        Log.i("VM", "RecipeViewVM cleared")
        super.onCleared()
    }

    fun onChangePortions(progress: Int) {
        _recipeLiveData.value = _recipeLiveData.value?.copy(portionsCount = progress)
    }
}