package com.example.myfood.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myfood.dao.RecipeDao
import com.example.myfood.entities.CategoryItems
import com.example.myfood.entities.Meal
import com.example.myfood.entities.MealsItems
import com.example.myfood.entities.Recipes
import com.example.myfood.entities.converter.CategoryListConverter
import com.example.myfood.entities.converter.MealListConverter


@Database(
    entities = [Recipes::class, CategoryItems::class, Meal::class, MealsItems::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS Category")
            }
        }
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        @JvmStatic
        fun getDataBase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, RecipeDatabase::class.java, "recipe.db"
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
