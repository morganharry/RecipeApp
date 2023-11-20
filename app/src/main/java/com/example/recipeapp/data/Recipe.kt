package com.example.recipeapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
) : Parcelable