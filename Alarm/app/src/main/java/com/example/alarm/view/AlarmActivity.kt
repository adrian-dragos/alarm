package com.example.alarm.view


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.R
import kotlinx.android.synthetic.main.alarm.*

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm)

        var alarmSound = MediaPlayer.create(this, R.raw.sound)
        alarmSound.start()
        alarmSound.isLooping = true
        cancel_alarm_button.setOnClickListener { cancelAlarm(alarmSound) }
    }

    private fun cancelAlarm(alarmSound: MediaPlayer) {
        alarmSound.isLooping = false
        alarmSound.stop()
        val intent = Intent(this, QRActivity::class.java)
        startActivity(intent)
    }
}