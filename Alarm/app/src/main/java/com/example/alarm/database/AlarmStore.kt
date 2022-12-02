package com.example.alarm.database

import com.example.alarm.Domain.Alarm
import com.example.alarm.Domain.AlarmRepository
import com.example.alarm.Domain.DayOfTheWeek

class AlarmStore(private val appDatabase: AppDatabase) : AlarmRepository {
    override fun getAll(): List<Alarm> {
        return appDatabase.alarmDao().getAll().map { toDomainModel(it) }
    }

    override fun addAlarm(alarm: Alarm) {
        return appDatabase.alarmDao().addAlarm(toDbModel(alarm))
    }

    override fun removeAlarm(alarmId: Long) {
        return appDatabase.alarmDao().removeAlarm(alarmId)
    }

    override fun updateIsAlarmActive(alarmId: Long, isActive: Boolean) {
        return appDatabase.alarmDao().updateIsActive(alarmId, isActive)
    }

    private fun toDbModel(alarm: Alarm): AlarmEntity {
        val id = alarm.id
        val description = alarm.description
        val isActive = alarm.isAlarmActive
        val monday = alarm.days!!.Monday
        val tuesday = alarm.days!!.Tuesday
        val wednesday = alarm.days!!.Wednesday
        val thursday = alarm.days!!.Thursday
        val friday = alarm.days!!.Friday
        val saturday = alarm.days!!.Saturday
        val sunday = alarm.days!!.Sunday
        val hour = alarm.hour
        val minute = alarm.minute
        val mission = alarm.mission

        return AlarmEntity(
            id, description, isActive,
            monday, tuesday, wednesday, thursday, friday, saturday, sunday,
            hour, minute, mission
        );
    }


    private fun toDomainModel(alarmEntity: AlarmEntity): Alarm {
        val id = alarmEntity.id
        val description = alarmEntity.description
        val isAlarmActive = alarmEntity.isActive
        val days = DayOfTheWeek(
            alarmEntity.Monday,
            alarmEntity.Tuesday,
            alarmEntity.Wednesday,
            alarmEntity.Thursday,
            alarmEntity.Friday,
            alarmEntity.Saturday,
            alarmEntity.Sunday
        )
        val hour = alarmEntity.hour
        val minute = alarmEntity.minute
        val mission = alarmEntity.mission

        return Alarm(
            id, description, isAlarmActive,
            days,
            hour, minute, mission
        );
    }
}