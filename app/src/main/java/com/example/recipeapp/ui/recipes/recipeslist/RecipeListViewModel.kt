package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel

data class RecipeListState(
    var stub: Int? = null,
)

class RecipeListViewModel (private val application: Application) : AndroidViewModel(application) {

}