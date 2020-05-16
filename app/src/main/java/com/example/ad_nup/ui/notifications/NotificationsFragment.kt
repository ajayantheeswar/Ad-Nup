package com.example.ad_nup.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ad_nup.*
import com.example.ad_nup.R
import com.example.ad_nup.database.AppDatabase
import com.example.ad_nup.database.models.transaction.GropedTransactionPojo
import com.example.ad_nup.database.models.transaction.TotalAmountPojo
import com.example.ad_nup.database.models.transaction.UserTransaction
import kotlinx.android.synthetic.main.activity_card_transaction.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import kotlinx.android.synthetic.main.fragment_notifications.view.editTextView_monthPicker_tag
import kotlinx.android.synthetic.main.fragment_notifications.view.editTextView_yearPicker_tag

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private lateinit var database: AppDatabase
    private lateinit var totalAmount : TextView
    private lateinit var transactionList : ListView
    private lateinit var queryButton : Button
    private lateinit var adapter : TagListAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        root.editTextView_monthPicker_tag.editText?.setText(getMonth())
        root.editTextView_yearPicker_tag.editText?.setText(getYear())

        queryButton = root.findViewById(R.id.button_tag_query)

        queryButton.setOnClickListener {
            handleFetch()
        }

        //Initialize the ListView
        database = AppDatabase.getDatabase(root.context)!!
        transactionList = root.findViewById(R.id.tag_list_view) as ListView
        adapter = TagListAdapter(root.context, ArrayList(
            database.userTransactionDao()!!
                .getAggregateTags(getMonth(), getYear())))

        totalAmount = root.findViewById(R.id.totalAmount)
        totalAmount.text = handleTotalPrice(getMonth(), getYear())

        transactionList.adapter = adapter
        return root
    }

    private fun handleFetch() {
        var month = this.editTextView_monthPicker_tag.editText?.text.toString()
        var year = this.editTextView_yearPicker_tag.editText?.text.toString()
        if(month.isNotEmpty() && year.isNotEmpty()){
            var newDataSet = database.userTransactionDao()?.getAggregateTags(month,year)
            adapter.changeDataSet(newDataSet as ArrayList<GropedTransactionPojo>)
            totalAmount.text = handleTotalPrice(month,year)
        }
    }

    private fun handleTotalPrice(month : String,year :String) : String{
        var total : TotalAmountPojo =
            database.userTransactionDao()?.getTotalAmountForTheMonth(month,year)!!
            return "Total Expense : Rs. ${total.total}"
    }
}
