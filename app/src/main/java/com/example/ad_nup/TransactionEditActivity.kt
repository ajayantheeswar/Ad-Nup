package com.example.ad_nup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceControl
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.ad_nup.database.AppDatabase
import com.example.ad_nup.database.models.transaction.UserTransaction
import com.example.ad_nup.getMonth
import kotlinx.android.synthetic.main.activity_card_transaction.*

class TransactionEditActivity : AppCompatActivity() {

    lateinit var  database : AppDatabase
    lateinit var transaction : UserTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_transaction)

        database = AppDatabase.getDatabase(this)!!

        var id : Int = intent.getIntExtra("ID",-1)

        if(id != -1){
            transaction = database.userTransactionDao()!!.getTransactionById(id)
            TrasactionAmount.text = "Rs. " + transaction.Amount.toString()
        }else{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        val COUNTRIES =
            arrayOf("Milk","Petrol","Health")

        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_menu_popup_item,
            COUNTRIES
        )

        val editTextFilledExposedDropdown: AutoCompleteTextView = findViewById(R.id.filled_exposed_dropdown_edit)
        editTextFilledExposedDropdown.setAdapter(adapter)

        proceed.setOnClickListener {
            val tag = editTextFilledExposedDropdown.text.toString()
            transaction.Tag = tag
            database.userTransactionDao()?.updateTrasaction(transaction)
            Toast.makeText(this,"Updated SuccessFully !!",Toast.LENGTH_SHORT).show()

            val resultIndent : Intent = Intent()
            resultIndent.putExtra("month",transaction.month)
            resultIndent.putExtra("year",transaction.year)
            setResult(Activity.RESULT_OK,resultIndent)
            finish()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
