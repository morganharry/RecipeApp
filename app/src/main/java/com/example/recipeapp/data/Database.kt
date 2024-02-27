package com.example.recipeapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Converters
import com.example.recipeapp.model.Recipe

@Database(entities = [Category::class, Recipe::class], version = 5)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoriesDao
    abstract fun recipesDao(): RecipesDao
}