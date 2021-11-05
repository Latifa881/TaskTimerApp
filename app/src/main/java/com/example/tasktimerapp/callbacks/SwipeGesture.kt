package com.example.tasktimerapp.callbacks

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

abstract class SwipeGesture(val context: Context): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    val deleteColor = ContextCompat.getColor(context, R.color.design_default_color_error)
    val deleteIcon = R.drawable.delete
    val updateColor = ContextCompat.getColor(context, R.color.green)
    val updateIcon = R.drawable.edit
   

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addSwipeLeftBackgroundColor(deleteColor)
            .addSwipeLeftLabel("Delete")
            .addSwipeLeftActionIcon(deleteIcon)
            .addSwipeRightBackgroundColor(updateColor)
            .addSwipeRightLabel("Update")
            .addSwipeRightActionIcon(updateIcon)
            .create()
            .decorate()
        super.onChildDraw(
            c!!, recyclerView!!,
            viewHolder!!, dX, dY, actionState, isCurrentlyActive
        )
    }
}

