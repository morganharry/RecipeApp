package com.example.recipeapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

data class RecipeUiState(
    var recipe: Recipe? = null,
    var progressSeekBar: Int = 1,
    var isFavorite: Boolean = false,
)

class RecipeViewModel : ViewModel() {

    init {
        Log.e("Recipevm","VM created")
    }

    private val _recipeLiveData = MutableLiveData<RecipeUiState>()
    private val  recipeLiveData: LiveData<RecipeUiState>
        get() = _recipeLiveData

    override fun onCleared() {
        Log.e("Recipevm","VM cleared")
        super.onCleared()
    }
}



