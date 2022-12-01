package com.example.alarm.database

import android.content.Context
import androidx.room.Room

class RoomDatabase {
    private var appDatabase: AppDatabase? = null

    fun getDb(context: Context): AppDatabase {
        if (appDatabase == null)
            appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "alarms-db")
                .allowMainThreadQueries()
                .build()

        return appDatabase!!
    }
}