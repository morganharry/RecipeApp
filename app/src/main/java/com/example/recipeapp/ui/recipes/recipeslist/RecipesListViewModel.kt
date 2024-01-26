package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import java.io.InputStream

data class RecipesListState(
    var categoryTitle: String? = null,
    var categoryDrawable: Drawable? = null,
    var recipesList: List<Recipe>? = null,
)

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository by lazy { RecipesRepository() }
    private var recipesList: List<Recipe>? = listOf()
    private var category: Category? = null

    val recipesListLiveData: LiveData<RecipesListState>
        get() = _recipesListLiveData
    private val _recipesListLiveData = MutableLiveData<RecipesListState>()

    init {
        Log.i("VM", "RecipesListVM created")
    }

    fun loadRecipesList(categoryId: Int) {
        val thread = Thread {
            category = repository.getCategory(categoryId)
            val categoryTitle = category?.title

            val categoryDrawable: Drawable? = try {
                val inputStream: InputStream? =
                    category?.imageUrl.let { it?.let { it1 -> this.application.assets?.open(it1) } }
                Drawable.createFromStream(inputStream, null)
            } catch (ex: Exception) {
                Log.e(this.javaClass.simpleName, ex.stackTraceToString())
                null
            }

            recipesList = repository.getRecipesByCategory(categoryId)
            _recipesListLiveData.postValue(
                RecipesListState(
                    categoryTitle,
                    categoryDrawable,
                    recipesList
                )
            )

            Log.i("!!!", "category: ${category.toString()}")
            Log.i("!!!", "recipesList: ${recipesList.toString()}")
        }

        thread.start()
    }

    override fun onCleared() {
        Log.i("VM", "RecipesListVM cleared")
        super.onCleared()
    }
}