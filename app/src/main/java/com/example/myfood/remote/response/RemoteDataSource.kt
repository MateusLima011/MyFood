package com.example.myfood.remote.response

import com.example.myfood.interfaces.GetDataService

class RemoteDataSource(private val apiService: GetDataService) {

    suspend fun getCategoryList(): CategoryResponse {
        return apiService.getCategoryList()
    }

    suspend fun getMealList(apiService: GetDataService, categories: String): MealsResponse {
        return apiService.getMealList(categories)
    }

    suspend fun getMealSpecific(apiService: GetDataService, id: String): MealSpecificResponse {
        return apiService.getSpecificItem(id)
    }
}