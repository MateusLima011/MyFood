package com.example.myfood.entities.converter

import androidx.room.TypeConverter
import com.example.myfood.entities.MealsItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealListConverter {
    @TypeConverter
    fun fromCategoryList(meals: List<MealsItems>?): String? {
        if (meals == null) {
            return null
        } else {
            val gson = Gson()
            val type = object : TypeToken<List<MealsItems>>() {}.type
            return gson.toJson(meals, type)
        }
    }

    @TypeConverter
    fun toCategoryList(mealsString: String?): List<MealsItems>? {
        if (mealsString == null) {
            return null
        } else {
            val gson = Gson()
            val type = object : TypeToken<List<MealsItems>>() {}.type
            return gson.fromJson(mealsString, type)
        }
    }
}