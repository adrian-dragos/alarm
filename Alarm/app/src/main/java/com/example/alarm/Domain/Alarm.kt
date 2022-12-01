package com.example.alarm.Domain

import java.util.Date

data class Alarm(
    val id: Long,
    val description: String,
    val isAlarmActive: Boolean,
    val days: DayOfTheWeek?,
    val Hour: Date,
    val mission: Mission
)