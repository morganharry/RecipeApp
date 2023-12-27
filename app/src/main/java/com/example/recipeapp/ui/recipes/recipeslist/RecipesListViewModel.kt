package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe

data class RecipesListState(
    var recipesList: List<Recipe>? = null,
)

class RecipesListViewModel (private val application: Application) : AndroidViewModel(application) {

    val recipesListData: LiveData<RecipesListState>
        get() = _recipesListData
    private val _recipesListData = MutableLiveData<RecipesListState>()

    init {

        Log.i("recipeslistvm", "VM created")
    }

    fun loadRecipesList(categoryId: Int) {
        val recipesList: List<Recipe> = STUB.getRecipesByCategoryId(categoryId)
        _recipesListData.value = RecipesListState(recipesList)

    }






    override fun onCleared() {
        Log.i("recipeslistvm", "VM cleared")
        super.onCleared()
    }
}