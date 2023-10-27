package com.example.myfood.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myfood.dao.RecipeDao
import com.example.myfood.entities.Category
import com.example.myfood.entities.CategoryItems
import com.example.myfood.entities.Meal
import com.example.myfood.entities.MealsItems
import com.example.myfood.entities.Recipes
import com.example.myfood.entities.converter.CategoryListConverter
import com.example.myfood.entities.converter.MealListConverter


@Database(
    entities = [Recipes::class, CategoryItems::class, Category::class, Meal::class, MealsItems::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        private var instance: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(context, RecipeDatabase::class.java, "recipe.db").build()
    }
}
/*
@Database(
    entities = [Recipes::class, CategoryItems::class, Category::class, Meal::class, MealsItems::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    companion object {
        private var recipesDatabase: RecipeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase? {
            recipesDatabase = Room.databaseBuilder(
                context,
                RecipeDatabase::class.java,
                "recipe.db"
            ).build()
            return recipesDatabase
//            if (recipesDatabase == null) {
//                recipesDatabase = Room.databaseBuilder(
//                    context,
//                    RecipeDatabase::class.java,
//                    "recipe.db"
//                ).build()
//            }
//            return recipesDatabase!!
        }
    }

    abstract fun recipeDao(): RecipeDao
}*/
