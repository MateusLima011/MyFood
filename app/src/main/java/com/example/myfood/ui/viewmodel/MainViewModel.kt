package com.example.myfood.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfood.repository.Repository
import com.example.myfood.ui.viewdata.CategoryItemsState
import com.example.myfood.ui.viewdata.MealState
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _categoryListLiveData = MutableLiveData<CategoryItemsState>()
    val categoryListLiveData = _categoryListLiveData

    private val _mealListLiveData = MutableLiveData<MealState>()
    val mealListLiveData = _mealListLiveData


    fun clearDataBase() {
        viewModelScope.launch {
            try {
                //repository.clearDatabase()
            } catch (e: Exception) {
                _categoryListLiveData.postValue(CategoryItemsState.Failure)
                _mealListLiveData.postValue(MealState.Failure)
            }
        }
    }

    suspend fun fetchCategoriesAndMeals() {
        val categoriesResult = repository.getCategories()
        val categoryItemState = if (categoriesResult.isSuccess) {
            CategoryItemsState.Success(categoriesResult.getOrElse {
                throw Exception("erro ao buscar categories: $it")
            })
        } else {
            CategoryItemsState.Failure
        }

        when (categoryItemState) {
            is CategoryItemsState.Success -> {
                _categoryListLiveData.postValue(categoryItemState)
            }

            is CategoryItemsState.Failure -> {
                _categoryListLiveData.postValue(categoryItemState)
            }
        }
    }

    suspend fun fetchMeals() {
        val mealsResult = repository.getMeals("Beef")
        val mealsItemsState = if (mealsResult.isSuccess) {
            MealState.Success(mealsResult.getOrElse {
                throw Exception("Erro ao buscar sub-categorias: $it")
            })
        } else {
            MealState.Failure
        }

        when (mealsItemsState) {
            is MealState.Success -> {
                _mealListLiveData.postValue(mealsItemsState)
            }

            is MealState.Failure -> {
                _mealListLiveData.postValue(mealsItemsState)
            }
        }
    }


}
