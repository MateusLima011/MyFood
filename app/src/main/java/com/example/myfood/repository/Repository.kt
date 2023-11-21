package com.example.myfood.repository

import com.example.myfood.entities.toCategoryItems
import com.example.myfood.entities.toMealsItems
import com.example.myfood.interfaces.GetDataService
import com.example.myfood.mappers.mapToViewData
import com.example.myfood.remote.response.LocalDataSource
import com.example.myfood.remote.response.MealSpecificData
import com.example.myfood.remote.response.MealsItemsData
import com.example.myfood.remote.response.RemoteDataSource
import com.example.myfood.retrofit.RetrofitClientInstance
import com.example.myfood.ui.viewdata.CategoriesViewData
import java.io.IOException

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getCategories(): Result<List<CategoriesViewData>> {
        try {
            val remoteCategories = remoteDataSource.getCategoryList().categories
            val existingCategories = localDataSource.getCategoriesFromLocal()
            val (_, newCategories) = remoteCategories.partition { remoteCategory ->
                existingCategories.none { it.idCategory == remoteCategory.idCategory }
            }
            if (newCategories.isNotEmpty()) {
                localDataSource.insertCategory(newCategories.toCategoryItems())
            }
            val updateCategories = localDataSource.getCategoriesFromLocal()
            return Result.success(updateCategories.mapToViewData())
        } catch (e: IOException) {
            throw DataFetchException("Failed to fetch categories", e)
        }
    }

    suspend fun getMeals(categoryName: String): Result<List<MealsItemsData>> {
        val apiService: GetDataService =
            RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
        try {
            localDataSource.clearDbMeal()
            val remoteMeals = remoteDataSource.getMealList(apiService, categoryName).meals
            val existingMeals = localDataSource.getMealsFromLocal(categoryName)
            val newMeals = remoteMeals.filter { remoteMeal ->
                existingMeals.none { it.idMeal == remoteMeal.idMeal }
            }

            if (newMeals.isNotEmpty()) {
                localDataSource.insertMeal(newMeals.toMealsItems(categoryName))
            }
            val updateMeals = localDataSource.getMealsFromLocal(categoryName)
            /*localDataSource.insertMeal(remoteMeals.toMealsItems(categoryName))
            val localMeals = localDataSource.getMealsFromLocal(categoryName)*/
            return Result.success(updateMeals.mapToViewData(categoryName))
        } catch (e: IOException) {
            throw DataFetchException("Failed to fetch meals", e)
        }
    }

    suspend fun getSpecificMeal(id: String): Result<MealSpecificData> {
        val apiService: GetDataService =
            RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
        try {
            val remoteCategories =
                remoteDataSource.getMealSpecific(apiService, id).meals.first()
            return Result.success(remoteCategories)
        } catch (e: IOException) {
            throw DataFetchException("Failed to Specific Meal", e)
        }
    }
}

class DataFetchException(message: String, cause: Throwable? = null) : Exception(message, cause)
