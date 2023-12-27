package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class FavoritesState(
    var stub: Int? = null,
)

class FavoritesViewModel (private val application: Application) : AndroidViewModel(application) {

    val favoritesData: LiveData<FavoritesState>
        get() = _favoritesData
    private val _favoritesData = MutableLiveData<FavoritesState>()

    init {
        Log.i("favoritesvm", "VM created")
    }

    override fun onCleared() {
        Log.i("favoritesvm", "VM cleared")
        super.onCleared()
    }
}