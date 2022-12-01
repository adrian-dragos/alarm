package com.example.alarm.Domain

import java.util.Date

data class Alarm(
    val description : String,
    val isAlarmActive: Boolean,
    val days: DayOfTheWeek,
    val Hour: Date
)