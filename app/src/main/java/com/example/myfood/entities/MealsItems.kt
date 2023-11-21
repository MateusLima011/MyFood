package com.example.myfood.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfood.remote.response.MealsItemsData

@Entity(tableName = "MealItems")
data class MealsItems(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int? = null,
    @ColumnInfo("idmeal") val idMeal: String,
    @ColumnInfo("categoryName") var categoryName: String?,
    @ColumnInfo("strmeal") val strMeal: String,
    @ColumnInfo("strmealthumb") val strMealThumb: String
)

fun List<MealsItemsData>.toMealsItems(categoryName: String): List<MealsItems> = kotlin.run {
    map {
        MealsItems(
            idMeal = it.idMeal,
            strMeal = it.strMeal,
            strMealThumb = it.strMealThumb,
            categoryName = categoryName
        )
    }
}