package com.example.alarm.database

import androidx.room.*

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarms")
    fun getAll(): List<AlarmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlarm(vararg alarm: AlarmEntity)

    @Query("DELETE FROM alarms WHERE id = :alarmId")
    fun removeAlarm(alarmId: Long)

    @Query("UPDATE alarms SET isActive = :isActive WHERE id = :alarmId")
    fun updateIsActive(alarmId: Long, isActive: Boolean)
}