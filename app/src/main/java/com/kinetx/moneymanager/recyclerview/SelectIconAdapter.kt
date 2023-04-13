package com.kinetx.moneymanager.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.dataclass.IconDataClass

class SelectIconAdapter (val listener: OnSelectIconListener) :
    ListAdapter<IconDataClass, SelectIconAdapter.MyViewHolder>(SelectIconItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectIconAdapter.MyViewHolder
    {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.select_icon_item,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectIconAdapter.MyViewHolder, position: Int)
    {
        val currentItem = getItem(position)
        holder.itemImage.setImageResource(currentItem.iconImage)
        holder.itemImage.setBackgroundColor(currentItem.iconColor)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val itemImage : ImageView = itemView.findViewById(R.id.select_category_item_image)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!= RecyclerView.NO_POSITION) {
                listener.onSelectIconClick(position)
            }
        }

    }

    interface OnSelectIconListener
    {
        fun onSelectIconClick(position: Int)
    }

}

class SelectIconItemCallBack : DiffUtil.ItemCallback<IconDataClass>()
{
    override fun areItemsTheSame(
        oldItem: IconDataClass,
        newItem: IconDataClass
    ): Boolean {
        return oldItem.iconImage == newItem.iconImage
    }

    override fun areContentsTheSame(
        oldItem: IconDataClass,
        newItem: IconDataClass
    ): Boolean {
        return oldItem == newItem
    }
}