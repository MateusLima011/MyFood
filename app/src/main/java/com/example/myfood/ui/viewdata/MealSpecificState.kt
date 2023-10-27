package com.example.myfood.ui.viewdata

import com.example.myfood.remote.response.MealSpecificData

sealed class MealSpecificState {
    data class Success(val data: MealSpecificData) : MealSpecificState()
    data object Failure : MealSpecificState()
}