package com.example.myfood.mappers

import com.example.myfood.entities.MealsItems
import com.example.myfood.remote.response.MealsItemsData

fun List<MealsItems>.mapToViewData(strCategory: String) = kotlin.run {
    map {
        MealsItemsData(
            strMeal = it.strMeal,
            strMealThumb = it.strMealThumb,
            idMeal = it.idMeal,
            categoryName = strCategory
        )
    }
}