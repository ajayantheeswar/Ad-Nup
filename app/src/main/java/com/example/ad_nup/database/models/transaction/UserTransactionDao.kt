package com.example.ad_nup.database.models.transaction

import androidx.room.*

data class GropedTransactionPojo(var tag : String , var total:Double)
data class TotalAmountPojo(var total:Double)

@Dao
interface UserTransactionDao {

    @Insert
    fun insertAll(vararg transactions: UserTransaction)

    @Query("SELECT * FROM UserTransaction Where month = (:month) and year = (:year)")
    fun getTransactionsByDate(month : String, year : String) : List<UserTransaction>

    @Query("SELECT * FROM UserTransaction Where tag = (:tag)")
    fun getTransactionsByTag(tag : String) : List<UserTransaction>

    @Query("SELECT tag as tag , SUM(amount) as total FROM UserTransaction Where month = (:month) and year = (:year) GROUP BY tag")
    fun getAggregateTags(month:String, year:String) : List<GropedTransactionPojo>

    @Delete
    fun delete(user: UserTransaction)

    @Query("SELECT * FROM UserTransaction WHERE TransactionId = (:id)")
    fun getTransactionById(id : Int) : UserTransaction

    @Update
    fun updateTrasaction(transaction: UserTransaction)

    @Query("Select SUM(amount) as total From UserTransaction Where month =  (:month) and year = (:year) ")
    fun getTotalAmountForTheMonth(month : String,year : String) : TotalAmountPojo
}