package com.example.recipeapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

data class RecipeUiState(
    var recipe: Recipe? = null,
    var progressSeekBar: Int = 1,
    var isFavorite: Boolean = false,
)

class RecipeViewModel : ViewModel() {

    init {
        Log.e("recipevm","VM created")
    }

    override fun onCleared() {
        Log.e("RecipeVM","VM cleared")
        super.onCleared()
    }
}
