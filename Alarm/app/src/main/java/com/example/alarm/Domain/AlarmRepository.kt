package com.example.alarm.Domain

interface AlarmRepository {
    fun getAll() : List<Alarm>
    fun addAlarm(alarm: Alarm)
    fun removeAlarm(alarm: Alarm)
}