package com.kinetx.moneymanager.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.dataclass.AccountListData

class AccountListRV(val listener : SelectAccountListRV) : RecyclerView.Adapter<AccountListRV.MyViewHolder>()  {

    private var _list = emptyList<AccountListData>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), OnClickListener{

        val accountName : TextView = itemView.findViewById(R.id.account_list_category_name)
        val accountIncome : TextView = itemView.findViewById(R.id.account_list_income)
        val accountExpense : TextView = itemView.findViewById(R.id.account_list_expense)
        val accountAmount : TextView = itemView.findViewById(R.id.account_list_amount)
        val accountImageButton : ImageView = itemView.findViewById(R.id.account_list_image_button)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION) {
                listener.onSelectAccountListRV(position)
            }
        }

    }

    interface SelectAccountListRV {
        fun onSelectAccountListRV(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.account_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = _list[position]
        holder.accountImageButton.setBackgroundColor(currentItem.categoryColor)
        holder.accountImageButton.setImageResource(currentItem.categoryImage)
        holder.accountName.text = currentItem.categoryName
        holder.accountIncome.text = currentItem.income.toString()
        holder.accountExpense.text = currentItem.expense.toString()
        holder.accountAmount.text = currentItem.amount.toString()
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    fun setData( c: List<AccountListData>)
    {
        this._list = c
        notifyDataSetChanged()
    }
}