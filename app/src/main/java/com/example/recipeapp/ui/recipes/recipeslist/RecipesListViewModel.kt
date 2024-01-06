package com.example.recipeapp.ui.recipes.recipeslist

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe
import java.io.IOException
import java.io.InputStream

data class RecipesListState(
    var categoryTitle: String? = null,
    var categoryDrawable: Drawable? = null,
    var recipesList: List<Recipe>? = null,
)

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {

    val recipesListData: LiveData<RecipesListState>
        get() = _recipesListData
    private val _recipesListData = MutableLiveData<RecipesListState>()

    init {
        Log.i("recipeslistvm", "VM created")
    }

    fun loadRecipesList(categoryId: Int) {
        val category = STUB.getCategoryId(categoryId)
        val categoryTitle = category?.title
        val categoryDrawable: Drawable?

        try {
            val inputStream: InputStream? =
                category?.imageUrl.let { it?.let { it1 -> this.application.assets?.open(it1) } }
            categoryDrawable = Drawable.createFromStream(inputStream, null)
        } catch (ex: IOException) {
            Log.e(this.javaClass.simpleName, ex.stackTraceToString())
            return
        }

        val recipesList: List<Recipe> = STUB.getRecipesByCategoryId(categoryId)

        _recipesListData.value = RecipesListState(categoryTitle, categoryDrawable, recipesList)
    }

    override fun onCleared() {
        Log.i("recipeslistvm", "VM cleared")
        super.onCleared()
    }
}