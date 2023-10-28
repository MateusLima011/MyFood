package com.example.myfood.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myfood.entities.converter.ListConverter
import com.example.myfood.entities.converter.MealListConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity(tableName = "Meal")
@Serializable
class Meal(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val categoryName: String,
    var meals: List<MealsItems>
)