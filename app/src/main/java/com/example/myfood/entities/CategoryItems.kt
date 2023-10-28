package com.example.myfood.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfood.remote.response.CategoryItemsData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity(tableName = "categoryitems")
data class CategoryItems(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int? = null,
    @ColumnInfo("idcategory") val idCategory: String,
    @ColumnInfo("strcategory") val category: String,
    @ColumnInfo("strcategorythumb") val categoryThumb: String,
    @ColumnInfo("strcategorydescription") val categoryDescription: String
)

fun List<CategoryItemsData>.toCategoryItems(): List<CategoryItems> = run {
    map {
        CategoryItems(
            idCategory = it.idCategory,
            category = it.strCategory,
            categoryThumb = it.strCategoryThumb,
            categoryDescription = it.strCategoryDescription
        )
    }
}
