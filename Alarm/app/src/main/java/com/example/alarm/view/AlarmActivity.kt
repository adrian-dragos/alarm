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

    override fun onBackPressed() {
        return
    }

    private fun cancelAlarm() {
        // TODO: uncomment this line to open steps mission
        //startActivity(Intent(this, StepsActivity::class.java))


        // TODO: uncomment this line to open qr scanning mission
        // startActivity(Intent(this, QRActivity::class.java))
    }
}