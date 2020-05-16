package com.example.ad_nup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ad_nup.database.AppDatabase


class StartActivity : AppCompatActivity() {

    var database: AppDatabase? = null
    private val REQUEST_CODE = 6162


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        database = AppDatabase.getDatabase(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (
                checkSelfPermission(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is Already is Granted
                doSomeRouteThing()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS) &&
                    shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)) {
                    Toast.makeText(
                        applicationContext,
                        "The Permission is necessary",
                        Toast.LENGTH_LONG
                    ).show()
                }
                requestPermissions(
                    arrayOf(Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS),
                    REQUEST_CODE
                )
            }
        } else {
            try {
                doSomeRouteThing()
            } catch (E: Exception) {
                Log.e(application.toString(), E.message)
            }
        }

    }

    private fun doSomeRouteThing(){
        if(isUserAvailable()){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }else{
            // go to UserRegistration
            startActivity(Intent(this, UserRegistation::class.java))
            finish()
        }
    }

    private fun isUserAvailable () : Boolean{
        return !database?.userDao()?.getUser()?.isEmpty()!!
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (permissions.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Cant DO SMS .. ", Toast.LENGTH_LONG)
            }else{
                doSomeRouteThing()
            }
        }
    }
}
