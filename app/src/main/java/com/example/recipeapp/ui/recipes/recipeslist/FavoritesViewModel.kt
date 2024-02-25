package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

data class FavoritesState(
    var recipesList: List<Recipe>? = null,
)

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository by lazy { RecipesRepository(application) }
    private var favList: List<Recipe>? = listOf()

    val favoritesLiveData: LiveData<FavoritesState>
        get() = _favoritesLiveData
    private val _favoritesLiveData = MutableLiveData<FavoritesState>()

    init {
        Log.i("VM", "FavoritesViewVM created")
    }

    fun loadRecipesList() {
        viewModelScope.launch {
            favList = repository.getFavoritesFromCache()

            _favoritesLiveData.postValue(FavoritesState(favList))
        }
        Log.i("!!!", "favList: ${favList.toString()}")
    }

    override fun onCleared() {
        Log.i("VM", "FavoritesViewVM cleared")
        super.onCleared()
    }
}