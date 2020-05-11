package com.sqisland.android.espresso.cat_names

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  var textView: TextView = itemView as TextView
}