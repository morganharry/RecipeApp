package com.example.recipeapp.ui.recipes

import android.content.res.Resources.getSystem
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

private val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

class RecyclerViewItemDecoration(
    lineColor: Int
) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        color = lineColor
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val dividerLeft = 8.px
        val dividerRight = parent.width - 8.px

        for (i in 0 until parent.childCount) {

            if (i != parent.childCount - 1) {
                val child: View = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                val dividerTop: Int = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + 1.px
                val rect = Rect(dividerLeft, dividerTop, dividerRight, dividerBottom)
                c.drawRect(rect, paint)
            }
        }
    }
}