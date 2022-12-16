package com.example.alarm.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.alarm.R
import com.example.alarm.database.AlarmStore
import com.example.alarm.database.RoomDatabase
import com.example.alarm.utils.AlarmReceiver
import com.example.alarm.view_model.HomeViewModel
import com.example.alarm.view_model.HomeViewModelFactory
import kotlinx.android.synthetic.main.main_activity_view.*
import java.util.*

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm)
    }
}