package com.example.myfood.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfood.repository.Repository
import com.example.myfood.ui.viewdata.MealSpecificState
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    private val _specificLiveData = MutableLiveData<MealSpecificState>()
    val specificLiveData = _specificLiveData

    fun fetchSpecificMeal(id: String) {
        viewModelScope.launch {
            val mealSpecificResult = repository.getSpecificMeal(id)
            val mealSpecificState = if (mealSpecificResult.isSuccess) {
                MealSpecificState.Success(mealSpecificResult.getOrElse {
                    throw Exception("Erro ao buscar meal especifico: $it")
                })
            } else {
                MealSpecificState.Failure
            }

            when (mealSpecificState) {
                is MealSpecificState.Success -> {
                    _specificLiveData.postValue(mealSpecificState)
                }

                is MealSpecificState.Failure -> {
                    _specificLiveData.postValue(mealSpecificState)
                }
            }
        }
    }
}