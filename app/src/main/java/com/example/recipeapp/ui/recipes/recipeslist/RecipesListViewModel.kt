package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.URL_IMAGES
import kotlinx.coroutines.launch

data class RecipesListState(
    var categoryTitle: String? = null,
    var categoryImageUrl: String? = null,
    var recipesList: List<Recipe> = listOf(),
    var isShowError: Boolean = false,
)

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository by lazy { RecipesRepository(application) }
    private var recipesList: List<Recipe> = listOf()
    private var recipesListServer: List<Recipe>? = listOf()
    private var category: Category? = null


    private val _recipesListLiveData = MutableLiveData<RecipesListState>()
    val recipesListLiveData: LiveData<RecipesListState> = _recipesListLiveData

    init {
        Log.i("VM", "RecipesListVM created")
    }

    fun loadRecipesList(categoryId: Int) {

        viewModelScope.launch {
            category = repository.getCategoryFromCache(categoryId)

            val categoryTitle = category?.title
            val categoryImageUrl =
                "$URL_IMAGES${category?.imageUrl}"

            recipesList = repository.getRecipesListByCategoryFromCache(categoryId)

            _recipesListLiveData.postValue(
                RecipesListState(
                    categoryTitle,
                    categoryImageUrl,
                    recipesList
                )
            )

            recipesListServer = repository.getRecipesByCategory(categoryId)

            if (recipesListServer.isNullOrEmpty()) {
                _recipesListLiveData.postValue(RecipesListState(isShowError = true))
            } else {
                recipesListServer?.forEach {
                    it.categoryId = categoryId
                }
                recipesListServer?.let { repository.insertRecipesListByCategory(it) }

                _recipesListLiveData.postValue(recipesListServer?.let {
                    RecipesListState(
                        categoryTitle,
                        categoryImageUrl,
                        recipesList = it
                    )
                })
            }
        }

        Log.i("!!!", "category: ${category.toString()}")
        Log.i("!!!", "recipesList: ${recipesList}")
    }

    override fun onCleared() {
        Log.i("VM", "RecipesListVM cleared")
        super.onCleared()
    }
}