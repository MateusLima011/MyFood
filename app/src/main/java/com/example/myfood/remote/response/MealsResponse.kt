package com.example.myfood.remote.response

data class MealsResponse(
    val meals:List<MealsItemsData>
)

data class MealsItemsData(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String
)