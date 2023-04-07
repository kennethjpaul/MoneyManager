package com.kinetx.moneymanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class SelectCategoryAdapter(val listener: OnSelectCategoryListener) : ListAdapter<SelectCategoryItem, SelectCategoryAdapter.MyViewHolder>(SelectCategoryItemCallBack())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.select_category_item,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currentItem = getItem(position)
        holder.itemImage.setImageResource(currentItem.itemImage)
        holder.itemImage.setBackgroundResource(currentItem.itemColor)
        holder.itemText.text = currentItem.itemTitle
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener
    {
        val itemImage : ImageView = itemView.findViewById(R.id.select_category_item_image)
        val itemText  : TextView = itemView.findViewById(R.id.select_category_item_text)


        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION) {
                listener.onSelectCategoryClick(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION) {
                listener.onSelectCategoryLongClick(position)
                return true
            }
            return false
        }

    }

    interface OnSelectCategoryListener
    {
        fun onSelectCategoryClick(position: Int)
        fun onSelectCategoryLongClick(position: Int)
    }
}

class SelectCategoryItemCallBack : DiffUtil.ItemCallback<SelectCategoryItem>()
{
    override fun areItemsTheSame(
        oldItem: SelectCategoryItem,
        newItem: SelectCategoryItem
    ): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(
        oldItem: SelectCategoryItem,
        newItem: SelectCategoryItem
    ): Boolean {
        return oldItem == newItem
    }
}