package com.kinetx.moneymanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.database.CategoryDatabase

class SelectCategoryAdapter(val listener: OnSelectCategoryListener) : RecyclerView.Adapter<SelectCategoryAdapter.MyViewHolder>()
{

    private var _catergoryList = emptyList<CategoryDatabase>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.select_category_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currentItem = _catergoryList[position]
        holder.itemImage.setImageResource(currentItem.categoryImage)
        holder.itemImage.setBackgroundColor(currentItem.categoryColor)
        holder.itemText.text = currentItem.categoryName
    }

    override fun getItemCount(): Int {
        return _catergoryList.size
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

    fun setData(category : List<CategoryDatabase>)
    {
        this._catergoryList = category
        notifyDataSetChanged()
    }


}
