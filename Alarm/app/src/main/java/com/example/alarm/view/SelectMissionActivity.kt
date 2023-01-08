package com.example.alarm.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.Domain.Mission
import com.example.alarm.R
import kotlinx.android.synthetic.main.select_mission.*

class SelectMissionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_mission)
        var selectedAlarmId: Long = intent.getLongExtra(ShowAddUpdateAlarmActivity.ALARM_ID, 0)
        val hour = intent.getIntExtra(ShowAddUpdateAlarmActivity.ALARM_HOUR, 0)
        val minute = intent.getIntExtra(ShowAddUpdateAlarmActivity.ALARM_MINUTE, 0)
        val days = intent.getBooleanArrayExtra(ShowAddUpdateAlarmActivity.ALARM_DAYS)
        val isActive = intent.getBooleanExtra(ShowAddUpdateAlarmActivity.IS_ALARM_ACTIVE, false)
        val alarmVolume = intent.getFloatExtra(ShowAddUpdateAlarmActivity.ALARM_VOLUME, 0F)
        val stepsCount = intent.getIntExtra(ShowAddUpdateAlarmActivity.STEP_COUNT, 0)

        steps_card.setOnClickListener {
            val intent = Intent(this@SelectMissionActivity, ConfigureStepMissionActivity::class.java).apply {
                putExtra(ShowAddUpdateAlarmActivity.ALARM_ID, selectedAlarmId)
                putExtra(ShowAddUpdateAlarmActivity.IS_ALARM_ACTIVE, isActive)
                putExtra(ShowAddUpdateAlarmActivity.ALARM_HOUR, hour)
                putExtra(ShowAddUpdateAlarmActivity.ALARM_MINUTE, minute)
                putExtra(
                    ShowAddUpdateAlarmActivity.ALARM_DAYS, days)
                putExtra(ShowAddUpdateAlarmActivity.ALARM_VOLUME, alarmVolume)
                putExtra(ShowAddUpdateAlarmActivity.ALARM_MISSION, Mission.Steps)
                putExtra(ShowAddUpdateAlarmActivity.STEP_COUNT, stepsCount)
            }
            startActivity(intent)
        }

        qr_code_card.setOnClickListener {
            val intent = Intent(this@SelectMissionActivity, ConfigureQRCodeMissionActivity::class.java).apply {
                putExtra(ShowAddUpdateAlarmActivity.ALARM_ID, selectedAlarmId)
                putExtra(ShowAddUpdateAlarmActivity.IS_ALARM_ACTIVE, isActive)
                putExtra(ShowAddUpdateAlarmActivity.ALARM_HOUR, hour)
                putExtra(ShowAddUpdateAlarmActivity.ALARM_MINUTE, minute)
                putExtra(
                    ShowAddUpdateAlarmActivity.ALARM_DAYS, days)
                putExtra(ShowAddUpdateAlarmActivity.ALARM_VOLUME, alarmVolume)
                putExtra(ShowAddUpdateAlarmActivity.ALARM_MISSION, Mission.Steps)
                putExtra(ShowAddUpdateAlarmActivity.STEP_COUNT, stepsCount)
            }
            startActivity(intent)
        }
    }
}