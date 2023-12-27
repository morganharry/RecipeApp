package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class RecipeListState(
    var stub: Int? = null,
)

class RecipeListViewModel (private val application: Application) : AndroidViewModel(application) {

    val recipeListData: LiveData<RecipeListState>
        get() = _recipeListData
    private val _recipeListData = MutableLiveData<RecipeListState>()

    init {
        Log.i("recipelistvm", "VM created")
    }

    override fun onCleared() {
        Log.i("recipelistvm", "VM cleared")
        super.onCleared()
    }
}