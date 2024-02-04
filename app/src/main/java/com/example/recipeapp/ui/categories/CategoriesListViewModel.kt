package com.example.recipeapp.ui.categories

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Category
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            categories = repository.getCategories()

            if (categories == null) {
                val text = "Ошибка получения данных"
                val duration = Toast.LENGTH_LONG
                Toast.makeText(application, text, duration).show()
            }
            _categoriesListLiveData.postValue(CategoriesListState(categories))
        }

        Log.i("!!!", "categories: ${categories.toString()}")
        Log.i("VM", "CategoriesListVM created")
    }

    override fun onCleared() {
        Log.i("VM", "CategoriesListVM cleared")
        super.onCleared()
    }
}