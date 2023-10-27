package com.example.myfood.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.R
import com.example.myfood.injection.getHomeViewModel
import com.example.myfood.ui.adapter.MainCategoryAdapter
import com.example.myfood.ui.adapter.SubCategoryAdapter
import com.example.myfood.ui.viewdata.CategoryItemsState
import com.example.myfood.ui.viewdata.MealState

class HomeActivity : AppCompatActivity() {
    val homeViewModel by lazy { getHomeViewModel() }

    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeViewModel.loadDataFromDb()
        homeViewModel.getMealDataFromDb("Beef")
        setupObservers()
    }

    private fun setupObservers() {
        homeViewModel.mainCategoryLiveData.observe(this) { state ->
            when (state) {
                is CategoryItemsState.Success -> {
                    mainCategoryAdapter.setData(state.data)
                    val rvMainCategory = findViewById<RecyclerView>(R.id.rv_main_category)
                    rvMainCategory.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    rvMainCategory.adapter = mainCategoryAdapter
                    mainCategoryAdapter.setClickListener(setupCategoryListener())
                }

                is CategoryItemsState.Failure -> handleFailure("Falha ao Buscar Categorias")
            }
        }
        homeViewModel.subCategoryLiveData.observe(this) { state ->
            when (state) {
                is MealState.Success -> {
                    subCategoryAdapter.setData(state.data)
                    val rvSubCategory = findViewById<RecyclerView>(R.id.rv_sub_category)
                    rvSubCategory.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    rvSubCategory.adapter = subCategoryAdapter
                    subCategoryAdapter.setClickListener(setupMealsListener())
                }

                is MealState.Failure -> handleFailure("Falha ao buscar Meals")
            }
        }
    }

    private fun setupCategoryListener() = object : MainCategoryAdapter.OnItemClickListener {
        override fun onClicked(categoryName: String) {
            homeViewModel.getMealDataFromDb(categoryName)
        }
    }

    private fun setupMealsListener() = object : SubCategoryAdapter.OnItemClickListener {
        override fun onClicked(idMeal: String) {
            Intent(this@HomeActivity, DetailActivity::class.java).apply {
                putExtra("id", idMeal)
            }.run(::startActivity)
        }
    }

    private fun handleFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
