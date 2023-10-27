package com.example.myfood.entities.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter<T> {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<T>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toList(json: String?): List<T>? {
        return json?.let {
            val type = object : TypeToken<List<T>>() {}.type
            gson.fromJson(it, type)
        }
    }
}