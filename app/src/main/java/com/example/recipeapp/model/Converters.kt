package com.example.recipeapp.model

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return Json.encodeToString(list ?: emptyList())
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }


    @TypeConverter
    fun fromIngredientsList(list: List<Ingredient>?): String {
        return Json.encodeToString(list ?: emptyList())
    }

    @TypeConverter
    fun toIngredientsList(value: String): List<Ingredient> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}