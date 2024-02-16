package com.example.recipeapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe

@Database(entities = [Category::class, Recipe::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoriesDao
    abstract fun recipesDao(): RecipesDao
}