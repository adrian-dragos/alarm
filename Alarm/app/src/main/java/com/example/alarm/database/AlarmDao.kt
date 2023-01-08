package com.example.alarm.database

import androidx.room.*
import com.example.alarm.Domain.Alarm

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarms ORDER BY IsActive DESC, hour * 60 + minute ASC")
    fun getAll(): List<AlarmEntity>

    @Query("SELECT * FROM alarms Where Id = :alarmId")
    fun getAlarm(alarmId: Long): AlarmEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlarm(vararg alarm: AlarmEntity)

    @Query("DELETE FROM alarms WHERE id = :alarmId")
    fun removeAlarm(alarmId: Long)

    @Query("UPDATE alarms SET isActive = :isActive WHERE id = :alarmId")
    fun updateIsActive(alarmId: Long, isActive: Boolean)

    @Update
    fun updateAlarm(alarmEntity: AlarmEntity)
}