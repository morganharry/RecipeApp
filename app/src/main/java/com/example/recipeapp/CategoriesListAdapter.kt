package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CategoriesListAdapter(private val dataSet: Category, private val context: CategoriesListFragment) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvCategoryItem: CardView
        val tvCategoryName: TextView
        val ivCategoryImage: ImageView

        init {
            // Определите прослушиватель кликов для представления ViewHolder
            cvCategoryItem = view.findViewById(R.id.CardView)
        }
    }

    // Создание новых представлений (вызывается менеджером макетов)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Создайте новое представление, которое определяет пользовательский интерфейс элемента списка.
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.item_category, viewGroup, false)

        return ViewHolder(view)
    }

    // Заменить содержимое представления (вызываемого менеджером макета)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Получите элемент из вашего набора данных в этой позиции и замените
        // содержимое представления с этим элементом
        viewHolder.cvCategoryItem.text = dataSet[position]
    }

    // Верните размер вашего набора данных (вызываемый менеджером компоновки)
    override fun getItemCount() = dataSet.size

}