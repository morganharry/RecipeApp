package com.example.recipeapp.ui.recipes

import android.content.res.Resources.getSystem
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

private val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

