package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.data.Recipe
import java.io.IOException
import java.io.InputStream

class RecipesListAdapter(
    private val dataSet: List<Recipe>,
    private val fragment: RecipesListFragment,
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