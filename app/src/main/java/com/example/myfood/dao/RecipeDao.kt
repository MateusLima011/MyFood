package com.example.myfood.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myfood.entities.CategoryItems
import com.example.myfood.entities.MealsItems

@Dao
interface RecipeDao {

    @Query("SELECT * FROM categoryitems ORDER BY strcategory ASC")
    suspend fun getAllCategory(): List<CategoryItems>

    @Query("SELECT * FROM mealitems WHERE categoryName = :categoryName ORDER BY strmeal ASC")
    suspend fun getMealList(categoryName: String): List<MealsItems>

    @Query("DELETE FROM categoryitems")
    suspend fun clearDb()

    @Query("DELETE FROM MealItems")
    suspend fun clearDbMeal()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryItems: List<CategoryItems>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealsItems: List<MealsItems>?)
}