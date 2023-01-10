package com.example.alarm.view


import com.example.alarm.utils.AudioPlay
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.R
import kotlinx.android.synthetic.main.alarm.*

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm)

        AudioPlay.playAudio(this, R.raw.sound)
        cancel_alarm_button.setOnClickListener { cancelAlarm() }
    }

    private fun cancelAlarm() {
        val intent = Intent(this, QRActivity::class.java)
        startActivity(intent)
    }
}