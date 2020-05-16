package com.example.ad_nup.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ad_nup.database.models.transaction.UserTransaction
import com.example.ad_nup.database.models.transaction.UserTransactionDao
import com.example.ad_nup.database.models.user.User
import com.example.ad_nup.database.models.user.UserDao


@Database(
    entities = [User::class,UserTransaction::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?
    abstract fun userTransactionDao() : UserTransactionDao?

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase::class.java, "word_database"
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}