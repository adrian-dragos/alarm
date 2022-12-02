package com.example.alarm.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.AlarmHomeActivity
import com.example.alarm.R
import kotlinx.android.synthetic.main.add_alarm_view.*

class ShowAddUpdateAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_alarm_view)
        time_picker.setIs24HourView(true);

        var data = intent
        var selectedAlarmId: Long = data.getLongExtra(ALARM_ID, 0)
        var isAlarmActive: Boolean = data.getBooleanExtra(IS_ALARM_ACTIVE, false)
        if (selectedAlarmId != 0L) {
            populateView(intent)
        }


        save_alarm_button.setOnClickListener {
            val returnIntent = Intent().apply {
                val hour: Int = time_picker.currentHour
                val minute: Int = time_picker.currentMinute
                putExtra(ALARM_ID, selectedAlarmId)
                putExtra(ALARM_HOUR, hour)
                putExtra(ALARM_MINUTE, minute)
                putExtra(
                    ALARM_DAYS, booleanArrayOf(
                    checkbox_Monday.isChecked,
                    checkbox_Tuesday.isChecked,
                    checkbox_Wednesday.isChecked,
                    checkbox_Thursday.isChecked,
                    checkbox_Friday.isChecked,
                    checkbox_Saturday.isChecked,
                    checkbox_Sunday.isChecked
                ))
                putExtra(IS_ALARM_ACTIVE, isAlarmActive)
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        checkbox_everyday.setOnClickListener {
            everyDayIsClicked()
        }
        checkbox_Monday.setOnClickListener{ shouldEveryDayCheckBoxBeChecked() }
        checkbox_Tuesday.setOnClickListener { shouldEveryDayCheckBoxBeChecked() }
        checkbox_Wednesday.setOnClickListener{ shouldEveryDayCheckBoxBeChecked() }
        checkbox_Thursday.setOnClickListener{ shouldEveryDayCheckBoxBeChecked() }
        checkbox_Friday.setOnClickListener { shouldEveryDayCheckBoxBeChecked() }
        checkbox_Saturday.setOnClickListener{ shouldEveryDayCheckBoxBeChecked() }
        checkbox_Sunday.setOnClickListener{ shouldEveryDayCheckBoxBeChecked() }
    }

    private fun everyDayIsClicked() {
        if (checkbox_everyday.isChecked) {
            checkbox_Monday.isChecked = true
            checkbox_Tuesday.isChecked = true
            checkbox_Wednesday.isChecked = true
            checkbox_Thursday.isChecked = true
            checkbox_Friday.isChecked = true
            checkbox_Saturday.isChecked = true
            checkbox_Sunday.isChecked = true
        } else {
            checkbox_Monday.isChecked = false
            checkbox_Tuesday.isChecked = false
            checkbox_Wednesday.isChecked = false
            checkbox_Thursday.isChecked = false
            checkbox_Friday.isChecked = false
            checkbox_Saturday.isChecked = false
            checkbox_Sunday.isChecked = false
        }
    }

    private fun shouldEveryDayCheckBoxBeChecked() {
        if (checkbox_everyday.isChecked) {
            checkbox_everyday.isChecked = false
        }

        if (!checkbox_everyday.isChecked && checkbox_Monday.isChecked &&
            checkbox_Tuesday.isChecked && checkbox_Wednesday.isChecked &&
            checkbox_Thursday.isChecked && checkbox_Friday.isChecked &&
            checkbox_Saturday.isChecked && checkbox_Sunday.isChecked) {
            checkbox_everyday.isChecked = true
        }
    }

    private fun populateView(data: Intent) {
        val hour = data.getIntExtra(ALARM_HOUR, 0)
        val minute = data.getIntExtra(ALARM_MINUTE, 0)
        val days = data.getBooleanArrayExtra(ALARM_DAYS)

        time_picker.currentHour = hour
        time_picker.currentMinute = minute
        checkbox_Monday.isChecked = days?.get(0) ?: false
        checkbox_Tuesday.isChecked = days?.get(1) ?: false
        checkbox_Wednesday.isChecked = days?.get(2) ?: false
        checkbox_Thursday.isChecked = days?.get(3) ?: false
        checkbox_Friday.isChecked = days?.get(4) ?: false
        checkbox_Saturday.isChecked = days?.get(5) ?: false
        checkbox_Sunday.isChecked = days?.get(6) ?: false

        shouldEveryDayCheckBoxBeChecked()
    }

    companion object {
        const val ALARM_ID = "alarm_id"
        const val ALARM_HOUR = "hour"
        const val ALARM_MINUTE = "time"
        const val ALARM_DAYS = "days of the week"
        const val IS_ALARM_ACTIVE = "is alarm active"
    }

}