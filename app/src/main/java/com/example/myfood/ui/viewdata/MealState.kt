package com.example.myfood.ui.viewdata

import com.example.myfood.entities.MealsItems
import com.example.myfood.remote.response.MealsItemsData

sealed class MealState {
    data class Success(val data: List<MealsItemsData>) : MealState()
    data object Failure : MealState()
}