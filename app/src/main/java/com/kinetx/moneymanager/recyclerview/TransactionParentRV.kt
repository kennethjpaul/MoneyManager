package com.kinetx.moneymanager.recyclerview

import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.dataclass.TransactionParentList
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.helpers.CommonOperations
import java.math.RoundingMode
import java.text.DecimalFormat

class TransactionParentRV(val listener: TransactionParentListener) : RecyclerView.Adapter<TransactionParentRV.MyViewHolder>(), TransactionChildRV.TransactionChildListener {

    var viewPool = RecyclerView.RecycledViewPool()

    private var _list = emptyList<TransactionParentList>()

    interface TransactionParentListener {
        fun onLongClickTransactionParent(position: Long, transactionType: TransactionType)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val itemDate : TextView = itemView.findViewById(R.id.transaction_item_parent_date)
        val itemRecycler : RecyclerView = itemView.findViewById(R.id.transaction_item_parent_recycler_view)
        val itemTotalDay : TextView = itemView.findViewById(R.id.transaction_item_parent_day_total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item_parent,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = _list[position]


        val myCalendar: Calendar = Calendar.getInstance()
        myCalendar.timeInMillis = currentItem.date

        val day = myCalendar.get(Calendar.DAY_OF_MONTH).toString()
        val month = CommonOperations.monthArray[myCalendar.get(Calendar.MONTH)]
        val year  = myCalendar.get(Calendar.YEAR).toString()

        holder.itemDate.text = "$day-$month-$year"

        val adapter = TransactionChildRV(this)
        holder.itemRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.itemRecycler.setHasFixedSize(true)
        holder.itemRecycler.adapter = adapter
        holder.itemRecycler.setRecycledViewPool(viewPool)
        adapter.setData(currentItem.transactionChildList)

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR

        holder.itemTotalDay.text = df.format(currentItem.amount).toString()

    }

    override fun getItemCount(): Int {
        return _list.size
    }

    override fun onLongClickTransactionChild(position: Long, transactionType: TransactionType) {
        listener.onLongClickTransactionParent(position, transactionType)
    }

    fun setData(it: List<TransactionParentList>) {
        this._list = it
        notifyDataSetChanged()
    }
}