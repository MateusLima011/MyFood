package com.example.myfood.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val categories: List<CategoryItemsData>
)

@Serializable
data class CategoryItemsData(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)