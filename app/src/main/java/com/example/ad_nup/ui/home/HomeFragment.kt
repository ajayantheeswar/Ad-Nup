package com.example.ad_nup.ui.home


import android.app.Activity.RESULT_OK
import android.content.Intent
import com.example.ad_nup.TransactionListViewAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.ad_nup.R
import com.example.ad_nup.database.AppDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlin.collections.ArrayList
import com.example.ad_nup.*
import com.example.ad_nup.database.models.transaction.UserTransaction

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var database: AppDatabase

    private lateinit var transactionList : ListView
    private lateinit var queryButton : Button
    private lateinit var adapter : TransactionListViewAdapter

    private val REQUEST_KEY = 6162

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        //initialize editText
        root.editTextView_monthPicker.editText?.setText(getMonth())
        root.editTextView_yearPicker.editText?.setText(getYear())

        queryButton = root.findViewById(R.id.button_transaction_query)

        queryButton.setOnClickListener {
            handleFetch()
        }

        //Initialize the ListView
        database = AppDatabase.getDatabase(root.context)!!
        transactionList = root.findViewById(R.id.transaction_list_view) as ListView
        adapter = TransactionListViewAdapter(root.context, ArrayList(
            database.userTransactionDao()!!
                .getTransactionsByDate(getMonth(), getYear())))

        transactionList.setOnItemClickListener { parent, view, position, id ->
            var userTransaction : UserTransaction = adapter.getItem(position) as UserTransaction
            var intent : Intent = Intent(root.context,TransactionEditActivity::class.java)
            intent.putExtra("ID",userTransaction.TransactionId)
            startActivityForResult(intent,REQUEST_KEY)
        }

        transactionList.adapter = adapter

        return root
    }

    private fun handleFetch() {
        var month = this.editTextView_monthPicker.editText?.text.toString()
        var year = this.editTextView_yearPicker.editText?.text.toString()
        if(month.isNotEmpty() && year.isNotEmpty()){
            refreshDataSource(month,year)
        }

    }

    private fun refreshDataSource(month:String, year:String){
        var newDataSet = database.userTransactionDao()?.getTransactionsByDate(month,year)
        adapter.changeDataSet(newDataSet as ArrayList<UserTransaction>)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_KEY -> {
                if(resultCode == RESULT_OK){
                    if (data != null) {
                        refreshDataSource(data.getStringExtra("month"),data.getStringExtra("year"))
                        Toast.makeText(context,"Updated SuccessFully",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
