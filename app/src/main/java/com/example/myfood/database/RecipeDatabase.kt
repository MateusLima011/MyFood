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
import com.example.myfood.entities.MealsItems
import com.example.myfood.entities.Recipes
import com.example.myfood.entities.converter.CategoryListConverter
import com.example.myfood.entities.converter.MealListConverter


@Database(
    entities = [Recipes::class, CategoryItems::class, MealsItems::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS Category")
                database.execSQL("DROP TABLE IF EXISTS Meals")
            }
        }

        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        @JvmStatic
        fun getDataBase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, RecipeDatabase::class.java, "recipe.db"
                ).addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
