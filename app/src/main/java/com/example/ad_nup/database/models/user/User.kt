package com.example.ad_nup.database.models.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "name")
    var Name: String?
)