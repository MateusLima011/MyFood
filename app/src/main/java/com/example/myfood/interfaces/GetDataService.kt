package com.example.myfood.interfaces

import com.example.myfood.entities.Meal
import com.example.myfood.remote.response.CategoryResponse
import com.example.myfood.remote.response.MealSpecificData
import com.example.myfood.remote.response.MealSpecificResponse
import com.example.myfood.remote.response.MealsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataService {

    @GET("categories.php")
    suspend fun getCategoryList(): CategoryResponse

    @GET("filter.php")
    suspend fun getMealList(@Query("c") category: String):MealsResponse

    @GET("lookup.php")
    suspend fun getSpecificItem(@Query("i") id: String): MealSpecificResponse
}