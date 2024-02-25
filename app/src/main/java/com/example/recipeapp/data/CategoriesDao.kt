package com.example.recipeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.model.Category

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE id IN (:categoryId)")
    fun loadById(categoryId: Int): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: List<Category>)
}