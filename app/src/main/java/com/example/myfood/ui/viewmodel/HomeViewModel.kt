package com.example.myfood.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfood.repository.Repository
import com.example.myfood.ui.viewdata.CategoryItemsState
import com.example.myfood.ui.viewdata.MealState
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _mainCategoryLiveData = MutableLiveData<CategoryItemsState>()
    private val _subCategoryLiveData = MutableLiveData<MealState>()
    val mainCategoryLiveData: LiveData<CategoryItemsState> get() = _mainCategoryLiveData
    val subCategoryLiveData: LiveData<MealState> get() = _subCategoryLiveData

    fun loadDataFromDb() {
        viewModelScope.launch {
            val categoriesResult = repository.getCategories()

            val categoryItemState = if (categoriesResult.isSuccess) {
                CategoryItemsState.Success(categoriesResult.getOrElse {
                    throw it
                })
            } else {
                CategoryItemsState.Failure
            }

            when (categoryItemState) {
                is CategoryItemsState.Success -> {
                    _mainCategoryLiveData.postValue(categoryItemState)
                }
                is CategoryItemsState.Failure -> {
                    _mainCategoryLiveData.postValue(categoryItemState)
                }
            }
        }
    }

    fun getMealDataFromDb(categoryName: String) {
        viewModelScope.launch {
            val mealsResult = repository.getMeals(categoryName)

            val mealItemState = if (mealsResult.isSuccess) {
                MealState.Success(mealsResult.getOrElse {
                    throw it
                })
            } else {
                MealState.Failure
            }

            when (mealItemState) {
                is MealState.Success -> {
                    _subCategoryLiveData.postValue(mealItemState)
                }

                is MealState.Failure -> {
                    _subCategoryLiveData.postValue(mealItemState)
                }
            }
        }
    }
}