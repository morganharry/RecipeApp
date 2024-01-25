package com.example.recipeapp.ui.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Category

data class CategoriesListState(
    var categoriesList: List<Category>? = null,
)

class CategoriesListViewModel(application: Application) :
    AndroidViewModel(application) {
    private val repository by lazy { RecipesRepository() }
    private var categories: List<Category>? = listOf()

    val categoriesListLiveData: LiveData<CategoriesListState>
        get() = _categoriesListLiveData
    private val _categoriesListLiveData = MutableLiveData<CategoriesListState>()

    init {
        val thread = Thread {
            categories = repository.getCategories()
            Log.i("!!!", "categories: ${categories.toString()}")
        }

        thread.start()

        _categoriesListLiveData.value = CategoriesListState(categories)
        Log.i("categorieslistvm", "VM created")
    }

    override fun onCleared() {
        Log.i("categorieslistvm", "VM cleared")
        super.onCleared()
    }
}