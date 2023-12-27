package com.example.recipeapp.ui.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Category

data class CategoriesListState(
    var categoriesList: List<Category>? = null,
)

class CategoriesListViewModel (private val application: Application) : AndroidViewModel(application) {

    val categoriesListLiveData: LiveData<CategoriesListState>
        get() = _categoriesListLiveData
    private val _categoriesListLiveData = MutableLiveData<CategoriesListState>()

    init {
        val categoriesList: List<Category> = STUB.getCategories()
        _categoriesListLiveData.value = CategoriesListState(categoriesList)
        Log.i("categorieslistvm", "VM created")
    }

    override fun onCleared() {
        Log.i("categorieslistvm", "VM cleared")
        super.onCleared()
    }
}