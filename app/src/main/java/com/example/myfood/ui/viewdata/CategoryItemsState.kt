package com.example.myfood.ui.viewdata

import com.example.myfood.remote.response.CategoryItemsData

sealed class CategoryItemsState {
    data class Success(val data: List<CategoryItemsData>) : CategoryItemsState()
    data object Failure : CategoryItemsState()
}

