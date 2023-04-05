package com.kinetx.moneymanager

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class SelectCategoryAdapter() :RecyclerView.Adapter<SelectCategoryAdapter.MyViewHolder>()
{
    var selectCategoryItemArray = ArrayList<SelectCategoryItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.select_category_item,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currentItem = selectCategoryItemArray[position]
        holder.itemImage.setImageResource(currentItem.itemImage)
        holder.itemImage.setBackgroundResource(currentItem.itemColor)
        holder.itemText.text = currentItem.itemTitle
    }

    override fun getItemCount(): Int
    {
        return selectCategoryItemArray.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val itemImage : ImageView = itemView.findViewById(R.id.select_category_item_image)
        val itemText  : TextView = itemView.findViewById(R.id.select_category_item_text)
    }
}