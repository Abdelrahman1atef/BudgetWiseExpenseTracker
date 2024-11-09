package com.example.budgetwiseexpensetracker.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.budgetwiseexpensetracker.R

class CustomSpinnerAdapter(
    context: Context,
    private val categories: Array<String>,
    val hidingItemIndex: Int = 0 // The index of the item you want to hide
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories) {

    var selectedPosition: Int = hidingItemIndex

    init {
        setDropDownViewResource(R.layout.item_spinner_dropdown)
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        if (position == hidingItemIndex) {
            view.visibility = View.INVISIBLE // Hide the first item (hint)
        } else {
            view.visibility = View.VISIBLE // Ensure other items are visible
        }
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.setTextColor(if (position == hidingItemIndex) Color.GRAY else Color.BLACK)
        return view
    }
}
