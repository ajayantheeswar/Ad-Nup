package com.example.ad_nup

import java.util.*

fun getDay() : String {
    return Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
}

fun getMonth() : String{

    return (Calendar.getInstance().get(Calendar.MONTH) + 1).toString()

}
fun getYear() : String{
    return Calendar.getInstance().get(Calendar.YEAR).toString()
}