package com.kinetx.moneymanager.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.database.TransactionDatabase

class TransactionListAdapter(val listener: OnTransactionListListener) : RecyclerView.Adapter<TransactionListAdapter.MyViewHolder>()
{

    private var _transactionList = emptyList<TransactionDatabase>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currentItem = _transactionList[position]
        holder.itemCategoryTwo.text = currentItem.transactionCategoryTwo.toString()
        holder.itemCategoryOne.text = currentItem.transactionCategoryOne.toString()
        holder.itemComment.text     = currentItem.transactionComment
        holder.itemDate.text        = currentItem.transactionDate.toString()
        holder.itemAmount.text      = currentItem.transactionAmount.toString()

    }

    override fun getItemCount(): Int
    {
        return _transactionList.size
    }

    fun setData(it: List<TransactionDatabase>) {
        this._transactionList = it
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnLongClickListener
    {
        val itemCategoryOne : TextView  = itemView.findViewById(R.id.transaction_list_category_one)
        val itemCategoryTwo : TextView  = itemView.findViewById(R.id.transaction_list_category_two)
        val itemComment : TextView      = itemView.findViewById(R.id.transaction_list_comment)
        val itemDate : TextView         = itemView.findViewById(R.id.transaction_list_date)
        val itemAmount : TextView       = itemView.findViewById(R.id.transaction_list_amount)

        init
        {
            itemView.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean
        {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION)
            {
                listener.onTransactionListLonClick(position)
                return true
            }
            return false
        }

    }

    interface OnTransactionListListener
    {
        fun onTransactionListLonClick(position: Int)
    }

}