package com.example.myfood.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.R
import com.example.myfood.injection.getHomeViewModel
import com.example.myfood.ui.adapter.MainCategoryAdapter
import com.example.myfood.ui.adapter.SubCategoryAdapter
import com.example.myfood.ui.viewdata.CategoryItemsState
import com.example.myfood.ui.viewdata.MealState
import com.facebook.shimmer.ShimmerFrameLayout

class HomeActivity : AppCompatActivity() {
    private val homeViewModel by lazy { getHomeViewModel(this) }

    private var mainCategoryAdapter = MainCategoryAdapter()
    private var subCategoryAdapter = SubCategoryAdapter()

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
                is CategoryItemsState.Loading -> onCategoryLoading(true)
                is CategoryItemsState.Success -> {
                    onCategoryLoading(false)
                    mainCategoryAdapter.setData(state.data)
                    val rvMainCategory = findViewById<RecyclerView>(R.id.rv_main_category)
                    rvMainCategory.adapter = mainCategoryAdapter
                    mainCategoryAdapter.setClickListener(setupCategoryListener())
                }

                is CategoryItemsState.Failure -> handleFailure("Falha ao Buscar Categorias")
            }
        }
        homeViewModel.subCategoryLiveData.observe(this) { state ->
            when (state) {
                is MealState.Loading -> onSubCategoryLoading(true)
                is MealState.Success -> {
                    onSubCategoryLoading(false)
                    subCategoryAdapter.setData(state.data)
                    val rvSubCategory = findViewById<RecyclerView>(R.id.rv_sub_category)
                    rvSubCategory.adapter = subCategoryAdapter
                    subCategoryAdapter.setClickListener(setupMealsListener())
                }

                is MealState.Failure -> handleFailure("Falha ao buscar Meals")
            }
        }
    }

    private fun onCategoryLoading(isLoading: Boolean) {
        val categoryGroup = findViewById<Group>(R.id.categoryGroup)
        val searchCategoryView = findViewById<CardView>(R.id.searchCategoryView)
        with(findViewById<ShimmerFrameLayout>(R.id.shimmerCategory)) {
            if (isLoading) showShimmer(true)
            else {
                searchCategoryView.visibility = View.VISIBLE
                categoryGroup.visibility = View.VISIBLE
                hideShimmer()
                visibility = View.GONE
            }
        }
    }

    private fun onSubCategoryLoading(isLoading: Boolean) {
        val subCategoryGroup = findViewById<Group>(R.id.subCategoryGroup)
        with(findViewById<ShimmerFrameLayout>(R.id.shimmerSubCategory)) {
            if (isLoading) showShimmer(true)
            else {
                subCategoryGroup.visibility = View.VISIBLE
                hideShimmer()
                visibility = View.GONE
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
        onCategoryLoading(false)
        onSubCategoryLoading(true)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
