package com.example.ad_nup.database.models.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserTransaction(

    @PrimaryKey(autoGenerate = true)
    var TransactionId: Int,

    @ColumnInfo(name= "date")
    var date: String?,

    @ColumnInfo(name = "month")
    var month: String?,

    @ColumnInfo(name = "year")
    var year: String?,

    @ColumnInfo(name = "tag")
    var Tag : String?,

    @ColumnInfo(name = "amount")
    var Amount : Double?
)