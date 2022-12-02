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

    fun addAlarm(hour: Int, minute: Int) {
        val id: Long = System.currentTimeMillis();
        val alarm = Alarm(
            id, "-",
            true,
            DayOfTheWeek(true, true, true, true, false, false, false),
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

}