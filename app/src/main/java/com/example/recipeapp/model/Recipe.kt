package com.example.recipeapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Parcelize
@Entity
data class Recipe(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "ingredients") val ingredients: List<Ingredient>,
    @ColumnInfo(name = "method") val method: List<String>,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
) : Parcelable {
    @IgnoredOnParcel
    @Transient
    @ColumnInfo(name = "categoryId") var categoryId: Int? = null

    @IgnoredOnParcel
    @Transient
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false
}