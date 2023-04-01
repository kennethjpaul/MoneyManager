package com.kinetx.moneymanager

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class SelectCategoryAdapter(private val SelectCategoryItemArray: ArrayList<SelectCategoryItem>) :RecyclerView.Adapter<SelectCategoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.select_category_item,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = SelectCategoryItemArray[position]
        holder.itemImage.setImageResource(currentItem.itemImage)
        holder.itemText.text = currentItem.itemTitle
    }

    override fun getItemCount(): Int {
        return SelectCategoryItemArray.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val itemImage : ImageView = itemView.findViewById(R.id.select_category_item_image)
        val itemText  : TextView = itemView.findViewById(R.id.select_category_item_text)
    }
}