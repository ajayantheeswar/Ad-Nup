package com.example.ad_nup.ui.dashboard

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ad_nup.R
import com.example.ad_nup.database.AppDatabase
import com.example.ad_nup.database.models.transaction.UserTransaction
import com.example.ad_nup.getDay
import com.example.ad_nup.getMonth
import com.example.ad_nup.getYear
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val COUNTRIES =
            arrayOf("Milk","Petrol","Health")

        val adapter = ArrayAdapter(
            root.context,
            R.layout.dropdown_menu_popup_item,
            COUNTRIES
        )

        val editTextFilledExposedDropdown: AutoCompleteTextView =
            root.findViewById(R.id.filled_exposed_dropdown)
        editTextFilledExposedDropdown.setAdapter(adapter)

        val btn = root.findViewById(R.id.button_addBill) as Button
        btn.setOnClickListener {
            //Log.i("ABINAYA","BUTTON WORKS")
            val amount = amountEditText.editText?.text.toString()
            val tag = editTextFilledExposedDropdown.text.toString()
            if(amount.isNotEmpty()){
                   val transaction : UserTransaction = UserTransaction(Amount = amount.toDouble(),
                            date = getDay(),
                            year = getYear(),
                            month = getMonth(),
                            Tag = tag ,TransactionId = 0)
                   AppDatabase.getDatabase(root.context)?.userTransactionDao()?.insertAll(transaction)
                   Toast.makeText(root.context,"Payment Added",Toast.LENGTH_SHORT).show()
                   amountEditText.editText?.setText("")
            }
        }
        return root
    }



}
