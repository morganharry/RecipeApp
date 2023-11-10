package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.io.InputStream

class CategoriesListAdapter(
    private val dataSet: MutableList<Category>,
    private val context: CategoriesListFragment
) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvCategoryItem: CardView
        val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
        val tvCategoryText: TextView = view.findViewById(R.id.tvCategoryText)
        val ivCategoryImage: ImageView = view.findViewById(R.id.ivCategoryImage)

        init {
            cvCategoryItem = view.findViewById(R.id.cvCategoryItem)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvCategoryName.text = dataSet[position].title
        viewHolder.tvCategoryText.text = dataSet[position].description

        try {
            val inputStream: InputStream? =
                context.activity?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.ivCategoryImage.setImageDrawable(drawable)
        } catch (ex: IOException) {
            println("Stack Trace")
            return
        }
    }

    override fun getItemCount() = dataSet.size
}