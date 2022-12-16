package com.example.alarm.view


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.AlarmHomeActivity
import com.example.alarm.R
import kotlinx.android.synthetic.main.alarm.*
import kotlinx.android.synthetic.main.main_activity_view.*

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm)

        var alarmSound = MediaPlayer.create(this, R.raw.sound)
        alarmSound.start()

        cancel_alarm_button.setOnClickListener { cancelAlarm(alarmSound) }
    }

    private fun cancelAlarm(alarmSound: MediaPlayer) {
        Log.d("TAG", "ajunge aici")
        alarmSound.stop()
        Log.d("TAG", "lalala")

        val intent = Intent(this, QRActivity::class.java)
        startActivity(intent)
    }
}