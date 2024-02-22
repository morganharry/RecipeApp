package com.example.recipeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.model.Recipe

@Dao
interface RecipesDao {

    @Query("SELECT * FROM recipe WHERE categoryId IN (:categoryId)")
    fun loadByCategoryId(categoryId: Int): List<Recipe>

    @Query("SELECT * FROM recipe WHERE isFavorite IS true")
    fun getFavorites(): List<Recipe>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipes: List<Recipe>)
}
