package com.example.myfood.ui.viewdata

sealed class CategoryItemsState {
    data class Success(val data: List<CategoriesViewData>) : CategoryItemsState()
    data object Failure : CategoryItemsState()
    data object Loading : CategoryItemsState()
}

