package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel

data class FavoritesState(
    var stub: Int? = null,
)

class FavoritesViewModel (private val application: Application) : AndroidViewModel(application) {

}