package com.example.myfood.repository

import com.example.myfood.entities.toCategoryItems
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
            localDataSource.insertCategory(remoteCategories.toCategoryItems())
            val localCategories = localDataSource.getCategoriesFromLocal()
            return Result.success(localCategories.mapToViewData())

        } catch (e: IOException) {
            throw DataFetchException("Failed to fetch categories", e)
        }
    }

    suspend fun getMeals(categoryName: String): Result<List<MealsItemsData>> {
        val apiService: GetDataService =
            RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
        try {
            val remoteMeals = remoteDataSource.getMealList(apiService, categoryName).meals
            return Result.success(remoteMeals)
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
