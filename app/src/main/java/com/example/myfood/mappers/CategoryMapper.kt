package com.example.myfood.mappers

import com.example.myfood.entities.CategoryItems
import com.example.myfood.ui.viewdata.CategoriesViewData

fun List<CategoryItems>.mapToViewData() = run {
    map {
        CategoriesViewData(
            idCategory = it.idCategory,
            category = it.category,
            categoryDescription = it.categoryDescription,
            categoryThumb = it.categoryThumb
        )
    }
}