package com.kinetx.moneymanager

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.dataclass.SelectCategoryData

class SelectCategoryAdapter(val listener: OnSelectCategoryListener, val identifier: String) : RecyclerView.Adapter<SelectCategoryAdapter.MyViewHolder>()
{

    private var _catergoryList = emptyList<SelectCategoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.select_category_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currentItem = _catergoryList[position]
        holder.itemImage.setImageResource(currentItem.categoryImageString)
        holder.itemCard.setCardBackgroundColor(currentItem.categoryColor)
        holder.itemText.text = currentItem.categoryName
    }

    override fun getItemCount(): Int {
        return _catergoryList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener
    {
        val itemImage : ImageView = itemView.findViewById(R.id.select_category_item_image)
        val itemText  : TextView = itemView.findViewById(R.id.select_category_item_text)
        val itemCard : CardView = itemView.findViewById(R.id.select_category_item_card)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION) {
                listener.onSelectCategoryClick(position,identifier)
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
        fun onSelectCategoryClick(position: Int, identifier: String)
        fun onSelectCategoryLongClick(position: Int)
    }

    fun setData(category : List<SelectCategoryData>)
    {
        this._catergoryList = category
        notifyDataSetChanged()
    }


}
