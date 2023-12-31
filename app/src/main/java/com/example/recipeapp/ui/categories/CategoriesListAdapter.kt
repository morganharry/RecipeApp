package com.example.recipeapp.ui.categories

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.model.Category
import java.io.IOException
import java.io.InputStream

class CategoriesListAdapter(
    private val fragment: CategoriesListFragment,
    var dataSet: List<Category> = listOf(),
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {

        fun onItemClick(categoryId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvCategoryItem: CardView
        val tvCategoryName: TextView
        val tvCategoryDescription: TextView
        val ivCategoryImage: ImageView

        init {
            cvCategoryItem = view.findViewById(R.id.cvCategoryItem)
            tvCategoryName = view.findViewById(R.id.tvCategoryNameItem)
            tvCategoryDescription = view.findViewById(R.id.tvCategoryTextItem)
            ivCategoryImage = view.findViewById(R.id.ivCategoryItem)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvCategoryName.text = dataSet[position].title
        viewHolder.tvCategoryDescription.text = dataSet[position].description
        val categoryId = dataSet[position].id

        try {
            val inputStream: InputStream? =
                fragment.context?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.ivCategoryImage.setImageDrawable(drawable)
        } catch (ex: IOException) {
            Log.e(this.javaClass.simpleName, ex.stackTraceToString())
            return
        }

        viewHolder.cvCategoryItem.setOnClickListener { itemClickListener?.onItemClick(categoryId) }
    }

    override fun getItemCount() = dataSet.size
}