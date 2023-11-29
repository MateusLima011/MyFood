package com.example.myfood.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfood.repository.Repository
import com.example.myfood.ui.viewdata.CategoryItemsState
import com.example.myfood.ui.viewdata.CategoryItemsState.Success
import com.example.myfood.ui.viewdata.MealState
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _mainCategoryLiveData = MutableLiveData<CategoryItemsState>()
    private val _subCategoryLiveData = MutableLiveData<MealState>()
    val mainCategoryLiveData: LiveData<CategoryItemsState> get() = _mainCategoryLiveData
    val subCategoryLiveData: LiveData<MealState> get() = _subCategoryLiveData

    fun loadDataFromDb() {
        viewModelScope.launch {
            _mainCategoryLiveData.postValue(CategoryItemsState.Loading)
            val categoriesResult = repository.getCategories()
            if (categoriesResult.isSuccess) {
                _mainCategoryLiveData.postValue(Success(categoriesResult.getOrElse { throw it }))
            } else {
                _mainCategoryLiveData.postValue(CategoryItemsState.Failure)
            }
        }
    }

    fun getMealDataFromDb(categoryName: String) {
        viewModelScope.launch {
            _subCategoryLiveData.postValue(MealState.Loading)
            val mealsResult = repository.getMeals(categoryName)
            if (mealsResult.isSuccess) {
                _subCategoryLiveData.postValue(MealState.Success(mealsResult.getOrElse { throw it }))
            } else {
                _subCategoryLiveData.postValue(MealState.Failure)
            }
        }
    }
}