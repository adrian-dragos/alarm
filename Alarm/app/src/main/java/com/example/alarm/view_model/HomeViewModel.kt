package com.example.alarm.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alarm.Domain.Alarm
import com.example.alarm.Domain.AlarmRepository
import com.example.alarm.Domain.DayOfTheWeek
import com.example.alarm.Domain.Mission

class HomeViewModel(private val alarmRepository: AlarmRepository) : ViewModel() {
    val alarmsLiveData = MutableLiveData<List<Alarm>>()

    fun retrieveAlarms() {
        val alarms = alarmRepository.getAll()
        alarmsLiveData.postValue(alarms)
    }

    fun addAlarm(hour: Int, minute: Int, days: BooleanArray?) {
        val id: Long = System.currentTimeMillis();
        val alarm = Alarm(
            id, "-",
            true,
            DayOfTheWeek(
                days?.get(0) ?: false,
                days?.get(1) ?: false,
                days?.get(2) ?: false,
                days?.get(3) ?: false,
                days?.get(4) ?: false,
                days?.get(5) ?: false,
                days?.get(6) ?: false
            ),
            hour,
            minute,
            Mission.Steps
        )
        alarmRepository.addAlarm(alarm)
        retrieveAlarms()
    }

    fun removeAlarm(alarmId: Long) {
        alarmRepository.removeAlarm(alarmId)
        retrieveAlarms()
    }

    fun updateIsAlarmActive(id: Long, isActive: Boolean) {
        alarmRepository.updateIsAlarmActive(id, isActive)
        retrieveAlarms()
    }


    fun updateAlarm(alarmId: Long, hour: Int, minute: Int, days: BooleanArray?, isActive: Boolean) {
        val alarm = Alarm(
            alarmId, "-",
            isActive,
            DayOfTheWeek(
                days?.get(0) ?: false,
                days?.get(1) ?: false,
                days?.get(2) ?: false,
                days?.get(3) ?: false,
                days?.get(4) ?: false,
                days?.get(5) ?: false,
                days?.get(6) ?: false
            ),
            hour,
            minute,
            Mission.Steps
        )
        alarmRepository.updateAlarm(alarm)
        retrieveAlarms()
    }

}