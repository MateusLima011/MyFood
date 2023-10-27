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
import com.example.myfood.entities.Category
import com.example.myfood.entities.CategoryItems
import com.example.myfood.entities.Recipes
import com.example.myfood.remote.response.CategoryItemsData

class MainCategoryAdapter : RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {

    var arrMainCategory = ArrayList<CategoryItemsData>()
    var listerner: OnItemClickListener? = null

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    fun setData(arrData: List<CategoryItemsData>) {
        arrMainCategory.clear()
        arrMainCategory.addAll(arrData)
    }

    fun setClickListener(listener1: OnItemClickListener) {
        listerner = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_main_category, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val tvDishName: TextView = holder.itemView.findViewById(R.id.tv_dish_name)
        val imgDish: ImageView = holder.itemView.findViewById(R.id.img_dish)

        tvDishName.text = arrMainCategory[position].strCategory

        Glide
            .with(holder.itemView.context)
            .load(arrMainCategory[position].strCategoryThumb)
            .into(imgDish)

        holder.itemView.rootView.setOnClickListener { listerner!!.onClicked(arrMainCategory[position].strCategory) }
    }

    interface OnItemClickListener {
        fun onClicked(categoryName: String)
    }
}

