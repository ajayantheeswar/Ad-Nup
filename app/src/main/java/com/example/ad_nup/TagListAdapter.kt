package com.example.ad_nup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ad_nup.R
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.ad_nup.database.models.transaction.GropedTransactionPojo


class TagListAdapter(var context: Context, var aggregateTransaction: ArrayList<GropedTransactionPojo>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.tag_item, parent, false)
        }else{
            view = convertView
        }

        val tag: TextView = view!!.findViewById(R.id.TextView_tag)
        tag.text = aggregateTransaction[position].tag
        tag.setBackgroundColor(getBackgroundColor(tag.text.toString()))

        val amount: TextView = view!!.findViewById(R.id.TextView_amount)
        amount.text = "Rs. " + aggregateTransaction[position].total.toString()

        return view
    }

    override fun getItem(position: Int): Any {
        return aggregateTransaction[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return aggregateTransaction.size
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

    fun changeDataSet(newTransaction : ArrayList<GropedTransactionPojo>){
        this.aggregateTransaction = newTransaction
        this.notifyDataSetChanged()
    }



}