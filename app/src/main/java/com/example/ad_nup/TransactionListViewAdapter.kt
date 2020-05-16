package com.example.ad_nup


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.ad_nup.R
import com.example.ad_nup.database.models.transaction.UserTransaction


class TransactionListViewAdapter(var context: Context,var transaction: ArrayList<UserTransaction>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false)
        }else{
            view = convertView
        }
        val date: TextView = view!!.findViewById(R.id.date)
        date.text = transaction[position].date

        val tag: TextView = view!!.findViewById(R.id.tag)
        tag.text = transaction[position].Tag
        tag.setBackgroundColor(getBackgroundColor(tag.text.toString()))

        val amount: TextView = view!!.findViewById(R.id.amount)
        amount.text = "Rs. " + transaction[position].Amount.toString()

        return view
    }

    override fun getItem(position: Int): Any {
        return transaction[position]
    }

    override fun getItemId(position: Int): Long {
        return transaction[position].TransactionId.toLong()
    }

    override fun getCount(): Int {
        return transaction.size
    }

    fun getBackgroundColor(tag: String) : Int{
        when(tag){
            "Milk" -> return context.resources.getColor(R.color.milk)
            "Health" -> return context.resources.getColor(R.color.health)
            "Petrol" -> return context.resources.getColor(R.color.petrol)
            else ->{
                return R.color.colorAccent
            }
        }
    }

    fun changeDataSet(newTransaction : ArrayList<UserTransaction>){
        this.transaction = newTransaction
        this.notifyDataSetChanged()
    }



}