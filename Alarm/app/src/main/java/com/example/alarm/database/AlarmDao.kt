package com.example.alarm.database

import androidx.room.*

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarms")
    fun getAll(): List<AlarmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlarm(vararg alarm: AlarmEntity)

    @Delete
    fun removeAlarm(alarm: AlarmEntity)
}