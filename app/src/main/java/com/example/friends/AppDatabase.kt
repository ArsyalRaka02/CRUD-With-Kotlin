package com.example.friends

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Friend::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun friendDao(): FriendDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(contex: Context) : AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(contex.applicationContext,
                        AppDatabase::class.java, "FriendAppDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }

}