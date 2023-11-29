package com.example.myfood.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide
import com.example.myfood.databinding.ActivityDetailBinding
import com.example.myfood.injection.getDetailsViewModel
import com.example.myfood.ui.viewdata.MealSpecificState

class DetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    val homeViewModel by lazy { getDetailsViewModel(this) }

    var youtubeLink = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupStatusBar()
        val id = intent.getStringExtra("id")
        id?.let { homeViewModel.fetchSpecificMeal(it) }
        obseverDetails()
        setupUi()
    }

    private fun setupUi() {
        binding.imgToolbarBtnBack.setOnClickListener { finish() }
        binding.btnYoutube.setOnClickListener {
            val uri = Uri.parse(youtubeLink)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun setupStatusBar() {
        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
    }

    fun obseverDetails() {
        homeViewModel.specificLiveData.observe(this) { state ->
            when (state) {
                is MealSpecificState.Success -> {
                    binding.tvCategory.text = state.data.strMeal
                    Glide.with(this).load(state.data.strMealThumb).into(binding.imgItem)

                    val ingredientList = mutableListOf<String>()
                    val strIngredients = listOf(
                        state.data.strIngredient1, state.data.strIngredient2,
                        state.data.strIngredient3, state.data.strIngredient4,
                        state.data.strIngredient5, state.data.strIngredient6,
                        state.data.strIngredient7, state.data.strIngredient8,
                        state.data.strIngredient9, state.data.strIngredient10,
                        state.data.strIngredient11, state.data.strIngredient12,
                        state.data.strIngredient13, state.data.strIngredient14,
                        state.data.strIngredient15, state.data.strIngredient16,
                        state.data.strIngredient17, state.data.strIngredient18,
                        state.data.strIngredient19, state.data.strIngredient20
                    )

                    val strMeasures = listOf(
                        state.data.strMeasure1, state.data.strMeasure2,
                        state.data.strMeasure3, state.data.strMeasure4,
                        state.data.strMeasure5, state.data.strMeasure6,
                        state.data.strMeasure7, state.data.strMeasure8,
                        state.data.strMeasure9, state.data.strMeasure10,
                        state.data.strMeasure11, state.data.strMeasure12,
                        state.data.strMeasure13, state.data.strMeasure14,
                        state.data.strMeasure15, state.data.strMeasure16,
                        state.data.strMeasure17, state.data.strMeasure18,
                        state.data.strMeasure19, state.data.strMeasure20
                    )

                    for (i in 0 until 20) {
                        val ingredient = strIngredients[i]
                        val measure = strMeasures[i]

                        if (ingredient.isNullOrBlank().not() && measure.isNullOrBlank().not()) {
                            ingredientList.add("$ingredient $measure")
                        }
                    }

                    val ingredient = ingredientList.joinToString("\n")
                    binding.tvIngredients.text = ingredient
                    binding.tvInstructions.text = state.data.strInstructions
                    youtubeLink = state.data.strSource.toString()
                }

                is MealSpecificState.Failure -> {}
            }
        }
    }
}
