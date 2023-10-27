package com.example.myfood.entities.converter

import androidx.room.TypeConverter
import com.example.myfood.entities.CategoryItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryListConverter {
    @TypeConverter
    fun fromCategoryList(category: List<CategoryItems>?): String? {
        if (category == null) {
            return null
        } else {
            val gson = Gson()
            val type = object : TypeToken<List<CategoryItems>>() {}.type
            return gson.toJson(category, type)
        }
    }

    @TypeConverter
    fun toCategoryList(categoryString: String?): List<CategoryItems>? {
        if (categoryString == null) {
            return null
        } else {
            val gson = Gson()
            val type = object : TypeToken<List<CategoryItems>>() {}.type
            return gson.fromJson(categoryString, type)
        }
    }
}
