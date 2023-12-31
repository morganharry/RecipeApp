package com.example.recipeapp.ui.recipes.recipeslist

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import java.io.IOException
import java.io.InputStream

class RecipesListAdapter(
    private val fragment: Fragment,
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

        try {
            val inputStream: InputStream? =
                fragment.context?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.ivRecipeImage.setImageDrawable(drawable)
        } catch (ex: IOException) {
            Log.e(this.javaClass.simpleName, ex.stackTraceToString())
            return
        }

        viewHolder.cvRecipeFragment.setOnClickListener { itemClickListener?.onItemClick(recipeId) }
    }

    override fun getItemCount() = dataSet.size
}