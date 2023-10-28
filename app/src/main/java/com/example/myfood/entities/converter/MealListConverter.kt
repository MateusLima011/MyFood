package com.example.myfood.entities.converter

import androidx.room.TypeConverter
import com.example.myfood.entities.MealsItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromMealList(meals: List<MealsItems>): String? {
        return gson.toJson(meals)
    }

    @TypeConverter
    fun toCategoryList(mealsString: String): List<MealsItems> {
        return gson.fromJson(mealsString, object : TypeToken<List<MealsItems>>() {}.type)
    }
}