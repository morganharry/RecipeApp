package com.example.recipeapp.ui.recipes.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.model.Ingredient

class IngredientsAdapter(
    var dataSet: List<Ingredient> = listOf(),
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity = 1

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
            .inflate(R.layout.item_ingredient, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvDescription.text = dataSet[position].description
        viewHolder.tvQuantity.text = checkNumberType(dataSet[position].quantity)
        viewHolder.tvUnitOfMeasure.text = dataSet[position].unitOfMeasure
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(progress: Int) {
        quantity = progress
        this.notifyDataSetChanged()
    }

    private fun checkNumberType(quantityForOnePortion: String): String {
        if (quantityForOnePortion.matches("""[-+]?[0-9]*\.?[0-9]+""".toRegex())) {
            val num = quantityForOnePortion.toDouble() * quantity
            return if (Regex("^[0-9]*[.,]0").matches(num.toString()))
                String.format("%.0f", num)
            else
                String.format("%.1f", num)
        }
        return quantityForOnePortion
    }
}