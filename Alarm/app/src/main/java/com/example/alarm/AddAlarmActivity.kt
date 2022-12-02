package com.example.alarm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.Domain.Alarm
import kotlinx.android.synthetic.main.add_alarm_view.*

class AddAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_alarm_view)

        time_picker.setIs24HourView(true);
        save_alarm_button.setOnClickListener {
            val returnIntent = Intent().apply {
                val hour: Int = time_picker.currentHour
                val minute: Int = time_picker.currentMinute
                putExtra(ALARM_HOUR, hour)
                putExtra(ALARM_MINUTE, minute)
                putExtra(ALARM_DAYS, booleanArrayOf(
                    checkbox_Monday.isChecked,
                    checkbox_Tuesday.isChecked,
                    checkbox_Wednesday.isChecked,
                    checkbox_Thursday.isChecked,
                    checkbox_Friday.isChecked,
                    checkbox_Saturday.isChecked,
                    checkbox_Sunday.isChecked
                ))
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


    companion object {
        const val ALARM_HOUR = "hour"
        const val ALARM_MINUTE = "time"
        const val ALARM_DAYS = "days of the week"
    }

}