package com.example.myfood.entities.converter

import androidx.room.TypeConverter
import com.example.myfood.entities.CategoryItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromCategoryList(category: List<CategoryItems>): String? {
        return gson.toJson(category)
    }

    @TypeConverter
    fun toCategoryList(categoryString: String): List<CategoryItems> {
        return gson.fromJson(categoryString, object : TypeToken<List<CategoryItems>>() {}.type)
    }
}
