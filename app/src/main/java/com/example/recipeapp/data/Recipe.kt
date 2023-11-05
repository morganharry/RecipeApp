package com.example.recipeapp

data class Recipe(
    val title: String,
    val ingredient: Ingredient,
    val method: String,
    val imageUrl: String,
)