package com.example.ad_nup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ad_nup.database.AppDatabase
import com.example.ad_nup.database.models.user.User
import kotlinx.android.synthetic.main.activity_user_registation.*

class UserRegistation : AppCompatActivity() {

    lateinit var database : AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registation)
        database = AppDatabase.getDatabase(this)!!
        button_continue.setOnClickListener {
            var name : String = outlinedTextField.editText?.text.toString()
            if(!name.isEmpty()) {
                var user: User =
                    User(6162, name)
                database.userDao()?.insertAll(user)
                goToDashboard()

            }else{
                this.showTost("Kindly Fill Name")
            }


        }
    }

    fun showTost(message : String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
    fun goToDashboard(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
