package com.kinetx.moneymanager.recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.dataclass.TransactionChildList
import com.kinetx.moneymanager.enums.TransactionType
import java.math.RoundingMode
import java.text.DecimalFormat

class TransactionChildRV(val listener : TransactionChildListener): RecyclerView.Adapter<TransactionChildRV.MyViewHolder>() {

    private var _list = emptyList<TransactionChildList>()

    interface TransactionChildListener {
        fun onLongClickTransactionChild(position: Long, transactionType: TransactionType)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) , View.OnLongClickListener {

        val itemCategoryOne : TextView = itemView.findViewById(R.id.transaction_item_child_subtitle)
        val itemCategoryTwo : TextView  = itemView.findViewById(R.id.transaction_item_child_title)
        val itemComment : TextView = itemView.findViewById(R.id.transaction_item_child_summary)
        val itemAmount : TextView = itemView.findViewById(R.id.transaction_item_child_amount)
        val itemType : View = itemView.findViewById(R.id.transaction_item_child_view)
        val itemIcon : ImageView = itemView.findViewById(R.id.transaction_item_child_icon)

        init {
            itemView.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION)
            {
                val trId = _list[position].transactionId
                val trType = _list[position].transactionType
                listener.onLongClickTransactionChild(trId,trType)
                return true
            }
            return false
        }

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = _list[position]
        holder.itemCategoryOne.text = currentItem.categoryOne
        holder.itemCategoryTwo.text = currentItem.categoryTwo
        holder.itemComment.text = currentItem.comments

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        holder.itemAmount.text = df.format(currentItem.amount).toString()
        holder.itemType.setBackgroundColor(currentItem.transactionColor)
        holder.itemIcon.setImageResource(currentItem.categoryImageInt)
        holder.itemIcon.setBackgroundColor(currentItem.categoryColor)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item_child,parent,false)
        return MyViewHolder(itemView)
    }

    fun setData(it: List<TransactionChildList>) {
        this._list = it
        notifyDataSetChanged()
    }
}