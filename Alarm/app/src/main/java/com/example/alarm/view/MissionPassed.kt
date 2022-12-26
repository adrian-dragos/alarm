package com.example.alarm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.R
import kotlinx.android.synthetic.main.main_activity_view.*
import kotlinx.android.synthetic.main.mission_passed.*

class MissionPassed: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mission_passed)

        close_app.setOnClickListener { closeApp() }
    }

    private fun closeApp() {
        finishAffinity()
    }
}
