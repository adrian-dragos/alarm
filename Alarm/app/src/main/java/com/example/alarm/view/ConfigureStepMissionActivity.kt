package com.example.alarm.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.Domain.Mission
import com.example.alarm.R
import com.example.alarm.database.AlarmStore
import com.example.alarm.database.RoomDatabase
import kotlinx.android.synthetic.main.configure_steps.*
import kotlin.math.roundToInt

class ConfigureStepMissionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configure_steps)

        configureNumberPicker()
        var selectedAlarmId: Long = intent.getLongExtra(ShowAddUpdateAlarmActivity.ALARM_ID, 0)


        save_steps.setOnClickListener() {
            saveCount(selectedAlarmId)
        }
    }

    private fun configureNumberPicker() {
        number_picker.minValue = 0
        number_picker.maxValue = 999

        val steps = intent.getIntExtra(ShowAddUpdateAlarmActivity.STEP_COUNT, 0)
        if (steps == 0) {
            number_picker.value = 3
        } else {
            number_picker.value = (steps - 10) / 10
        }
        number_picker.wrapSelectorWheel = false;

        val list = mutableListOf<String>("10")
        for (i in 20..10000 step 10)
            list.add(i.toString())
        number_picker.displayedValues = list.toTypedArray()

    }

    private fun saveCount(selectedAlarmId: Long) {

        var selectedAlarmId: Long = intent.getLongExtra(ShowAddUpdateAlarmActivity.ALARM_ID, 0)
        val hour = intent.getIntExtra(ShowAddUpdateAlarmActivity.ALARM_HOUR, 0)
        val minute = intent.getIntExtra(ShowAddUpdateAlarmActivity.ALARM_MINUTE, 0)
        val days = intent.getBooleanArrayExtra(ShowAddUpdateAlarmActivity.ALARM_DAYS)
        val isActive = intent.getBooleanExtra(ShowAddUpdateAlarmActivity.IS_ALARM_ACTIVE, false)
        val alarmVolume = intent.getFloatExtra(ShowAddUpdateAlarmActivity.ALARM_VOLUME, 0F)
        val stepsCount:Int = (number_picker.value * 10) + 10

        val intent = Intent(this, ShowAddUpdateAlarmActivity::class.java).apply {
            putExtra(ShowAddUpdateAlarmActivity.ALARM_ID, selectedAlarmId)
            putExtra(ShowAddUpdateAlarmActivity.IS_ALARM_ACTIVE, isActive)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_HOUR, hour)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_MINUTE, minute)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_DAYS, days)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_VOLUME, alarmVolume.toInt())
            putExtra(ShowAddUpdateAlarmActivity.ALARM_MISSION, Mission.Steps)
            putExtra(ShowAddUpdateAlarmActivity.STEP_COUNT, stepsCount)
            putExtra(ShowAddUpdateAlarmActivity.IS_SETTING_MISSION, true)
        }
        startActivity(intent)
    }

}