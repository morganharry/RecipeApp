package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.data.Ingredient

class IngredientsAdapter(
    private val dataSet: List<Ingredient>,
    private val fragment: RecipeFragment
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDescription: TextView
        val tvQuantity: TextView
        val tvUnitOfMeasure: TextView

        init {
            tvDescription = view.findViewById(R.id.tvDescription)
            tvQuantity = view.findViewById(R.id.tvQuantity)
            tvUnitOfMeasure = view.findViewById(R.id.tvUnitOfMeasure)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredients, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.tvDescription.text = dataSet[position].description
        viewHolder.tvQuantity.text = dataSet[position].quantity
        viewHolder.tvUnitOfMeasure.text = dataSet[position].unitOfMeasure
    }

    override fun getItemCount() = dataSet.size
}
