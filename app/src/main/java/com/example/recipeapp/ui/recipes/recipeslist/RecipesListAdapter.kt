package com.example.recipeapp.ui.recipes.recipeslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.URL_IMAGES

class RecipesListAdapter(
    var dataSet: List<Recipe> = listOf(),
) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {

        fun onItemClick(recipeId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvRecipeFragment: CardView
        val tvRecipeName: TextView
        val ivRecipeImage: ImageView

        init {
            cvRecipeFragment = view.findViewById(R.id.cvRecipeItem)
            tvRecipeName = view.findViewById(R.id.tvRecipeNameItem)
            ivRecipeImage = view.findViewById(R.id.ivRecipeItem)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvRecipeName.text = dataSet[position].title
        val recipeId = dataSet[position].id

        val recipeImageUrl =
            "$URL_IMAGES${dataSet[position].imageUrl}"

        Glide.with(viewHolder.itemView.context)
            .load(recipeImageUrl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_error)
            .into(viewHolder.ivRecipeImage)

        viewHolder.cvRecipeFragment.setOnClickListener { itemClickListener?.onItemClick(recipeId) }
    }

    override fun getItemCount() = dataSet.size
}