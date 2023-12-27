package com.example.recipeapp.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.recipeapp.model.Recipe

data class CategoriesListState(
    var stub: Int? = null,
)

class CategoriesListViewModel (private val application: Application) : AndroidViewModel(application) {

}