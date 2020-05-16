package com.example.ad_nup.database.models.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ad_nup.database.models.user.User

@Dao
interface UserDao {

    @Insert
    fun insertAll(vararg users: User)

    @Query("SELECT * FROM user")
    fun getUser() : List<User>

    @Delete
    fun delete(user: User)
}