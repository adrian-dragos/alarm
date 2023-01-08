package com.example.alarm.Domain

interface AlarmRepository {
    fun getAll() : List<Alarm>
    fun getAlarm(alarmId: Long): Alarm
    fun addAlarm(alarm: Alarm)
    fun removeAlarm(alarmId: Long)
    fun updateIsAlarmActive(alarmId: Long, isActive: Boolean)
    fun updateAlarm(alarm: Alarm)
}