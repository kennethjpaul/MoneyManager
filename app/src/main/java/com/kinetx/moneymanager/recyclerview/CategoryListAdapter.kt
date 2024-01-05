package com.kinetx.moneymanager.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.dataclass.CategoryListData

class CategoryListAdapter (val listener: OnSelectCategoryListListener) : RecyclerView.Adapter<CategoryListAdapter.MyViewHolder>()
{

    private var _list = emptyList<CategoryListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = _list[position]
        holder.itemCategoryName.text = currentItem.categoryName
        holder.itemAmount.text = String.format("%.2f",currentItem.amount)
        holder.itemImageButton.setImageResource(currentItem.categoryImage)
        holder.itemImageButton.setBackgroundColor(currentItem.categoryColor)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {

        val itemImageButton :ImageView = itemView.findViewById(R.id.category_list_image_button)
        val itemCategoryName : TextView = itemView.findViewById(R.id.category_list_category_name)
        val itemAmount : TextView = itemView.findViewById(R.id.category_list_amount)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION) {
                listener.onSelectCategoryListClick(position)
            }
        }


    }

    interface OnSelectCategoryListListener
    {
        fun onSelectCategoryListClick(position:Int)
    }

    fun setData( c: List<CategoryListData>)
    {
        this._list = c
        notifyDataSetChanged()
    }

}