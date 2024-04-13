package com.kinetx.moneymanager.recyclerview

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.dataclass.CategoryBudgetData
import com.kinetx.moneymanager.dataclass.CategorySummaryListData

class CategoryBudgetRV(val listener : OnSelectCategoryBudgetListener) : RecyclerView.Adapter<CategoryBudgetRV.MyViewHolder>() {

    private var _list = emptyList<CategoryBudgetData>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val itemImageButton : ImageView = itemView.findViewById(R.id.category_budget_image_button)
        val itemCategoryName : TextView = itemView.findViewById(R.id.category_budget_category_name)
        val itemAmount : TextView = itemView.findViewById(R.id.category_budget_amount)
        val itemBudget : TextView = itemView.findViewById(R.id.category_budget_budget)
        val itemProgress : ProgressBar = itemView.findViewById(R.id.category_budget_progressBar)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION) {
                listener.onSelectCategoryBudgetClick(position)
            }
        }

    }

    interface OnSelectCategoryBudgetListener {
        fun onSelectCategoryBudgetClick(position : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_budget,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = _list[position]
        holder.itemCategoryName.text = currentItem.categoryName
        holder.itemAmount.text = String.format("%.2f",currentItem.amount)
        holder.itemImageButton.setImageResource(currentItem.categoryImage)
        holder.itemImageButton.setBackgroundColor(currentItem.categoryColor)
        holder.itemBudget.text = currentItem.budget
        holder.itemProgress.progress = currentItem.progressValue
        holder.itemProgress.progressTintList = ColorStateList.valueOf(currentItem.progressColor)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    fun setData( c: List<CategoryBudgetData>)
    {
        this._list = c
        notifyDataSetChanged()
    }
}