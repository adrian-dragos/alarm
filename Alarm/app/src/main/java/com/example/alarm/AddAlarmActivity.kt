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
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    companion object {
        const val ALARM_HOUR = "hour"
        const val ALARM_MINUTE = "time"
    }

}