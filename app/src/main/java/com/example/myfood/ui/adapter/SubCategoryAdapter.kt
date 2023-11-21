package com.example.myfood.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfood.R
import com.example.myfood.entities.MealsItems
import com.example.myfood.entities.Recipes
import com.example.myfood.remote.response.MealsItemsData

class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {

    private lateinit var context: Context
    private var arrSubCategory = ArrayList<MealsItemsData>()
    var listerner: OnItemClickListener? = null

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    fun setData(arrData: List<MealsItemsData>) {
        arrSubCategory = arrData as ArrayList<MealsItemsData>
    }

    fun setClickListener(listener1: OnItemClickListener) {
        listerner = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        context = parent.context
        return RecipeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_sub_category, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val tvDishName: TextView = holder.itemView.findViewById(R.id.tv_dish_name_sub)
        tvDishName.text = arrSubCategory[position].strMeal

        val imgDish: ImageView = holder.itemView.findViewById(R.id.img_dish_sub)

        Glide
            .with(context)
            .load(arrSubCategory[position].strMealThumb)
            .into(imgDish)

        holder.itemView.rootView.setOnClickListener { listerner!!.onClicked(arrSubCategory[position].idMeal) }
    }

    interface OnItemClickListener {
        fun onClicked(idMeal: String)
    }
}