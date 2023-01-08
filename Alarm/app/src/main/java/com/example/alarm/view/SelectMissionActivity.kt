package com.example.alarm.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.R
import kotlinx.android.synthetic.main.select_mission.*

class SelectMissionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_mission)

        steps_card.setOnClickListener {
            val intent = Intent(this@SelectMissionActivity, ConfigureStepMissionActivity::class.java)
            startActivity(intent)
        }

        qr_code_card.setOnClickListener {
            val intent = Intent(this@SelectMissionActivity, ConfigureQRCodeMissionActivity::class.java)
            startActivity(intent)
        }
    }
}