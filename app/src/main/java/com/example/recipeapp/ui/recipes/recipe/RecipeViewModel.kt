package com.example.recipeapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

data class RecipeState(
    var recipe: Recipe? = null,
    var progressSeekBar: Int = 1,
    var isFavorite: Boolean = false,
)

class RecipeViewModel : ViewModel() {

    var _recipeLiveData = MutableLiveData<RecipeState>()
    private val recipeLiveData: LiveData<RecipeState>
        get() = _recipeLiveData

    init {
        Log.i("recipevm", "VM created")
        recipeLiveData.value
    }

    override fun onCleared() {
        Log.i("recipevm", "VM cleared")
        super.onCleared()
    }
}