package com.example.alarm.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.alarm.Domain.Mission
import java.util.*

@Entity(tableName = "alarms")
class AlarmEntity(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "isActive")
    val isActive: Boolean,

    @ColumnInfo(name = "Monday")
    val Monday: Boolean,
    @ColumnInfo(name = "Tuesday")
    val Tuesday: Boolean,
    @ColumnInfo(name = "Wednesday")
    val Wednesday: Boolean,
    @ColumnInfo(name = "Thursday")
    val Thursday: Boolean,
    @ColumnInfo(name = "Friday")
    val Friday: Boolean,
    @ColumnInfo(name = "Saturday")
    val Saturday: Boolean,
    @ColumnInfo(name = "Sunday")
    val Sunday: Boolean,

    @ColumnInfo(name = "Hour")
    val hour: Int,
    @ColumnInfo(name = "Minute")
    val minute: Int,
    @ColumnInfo(name = "Mission")
    val mission: Mission
) {}