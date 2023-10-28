package com.example.myfood.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfood.remote.response.MealsItemsData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity(tableName = "MealItems")
@Serializable
data class MealsItems(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val idMeal: String,

    @ColumnInfo(name = "categoryName")
    val categoryName: String,

    val strMeal: String,
    val strMealThumb: String
)

fun MealsItems.toMealsItemsData(): MealsItemsData {
    return MealsItemsData(
        idMeal = this.idMeal,
        strMeal = this.strMeal,
        strMealThumb = this.strMealThumb
    )
}