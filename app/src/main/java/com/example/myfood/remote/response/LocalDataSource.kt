package com.example.myfood.remote.response

import com.example.myfood.dao.RecipeDao
import com.example.myfood.entities.CategoryItems
import com.example.myfood.entities.MealsItems

class LocalDataSource(private val recipeDao: RecipeDao) {

    suspend fun getCategoriesFromLocal(): List<CategoryItems> {
        return recipeDao.getAllCategory()
    }

    suspend fun insertCategory(categories: List<CategoryItems>) {
        recipeDao.insertCategory(categories)
    }

    suspend fun getMealsFromLocal(categoryName: String): List<MealsItems> {
        return recipeDao.getMealList(categoryName)
    }

    suspend fun insertMeal(meals: List<MealsItems>) {
        recipeDao.insertMeal(meals)
    }
    /*
        suspend fun getSpecificMealList(categoryName: String): List<MealsItems> {
            return recipeDao.getSpecificMealList(categoryName)
        }
    */

    suspend fun clearDb() {
        recipeDao.clearDb()
    }

    suspend fun clearDbMeal() {
        recipeDao.clearDbMeal()
    }
}