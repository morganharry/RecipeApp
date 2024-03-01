package com.example.recipeapp.ui.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Category
import kotlinx.coroutines.launch

data class CategoriesListState(
    var categoriesList: List<Category> = listOf(),
    var isShowError: Boolean = false,
)

class CategoriesListViewModel(application: Application) :
    AndroidViewModel(application) {
    private val repository by lazy { RecipesRepository(application) }
    private var categories: List<Category> = listOf()
    private var categoriesServer: List<Category>? = listOf()

    private val _categoriesListLiveData = MutableLiveData<CategoriesListState>()
    val categoriesListLiveData: LiveData<CategoriesListState> = _categoriesListLiveData

    init {
        viewModelScope.launch {
            categories = repository.getCategoriesFromCache()

            _categoriesListLiveData.postValue(CategoriesListState(categories))

            categoriesServer = repository.getCategories()

            if (categoriesServer.isNullOrEmpty()) {
                _categoriesListLiveData.postValue(CategoriesListState(isShowError = true))
            } else {
                categoriesServer?.let { repository.insertCategories(it) }
                _categoriesListLiveData.postValue(categoriesServer?.let { CategoriesListState(it) })
            }
        }

        Log.i("!!!", "categories: ${categories}")
        Log.i("VM", "CategoriesListVM created")
    }

    override fun onCleared() {
        Log.i("VM", "CategoriesListVM cleared")
        super.onCleared()
    }
}