package com.example.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarm.Domain.Alarm
import com.example.alarm.Domain.DayOfTheWeek
import com.example.alarm.Domain.Mission
import kotlinx.android.synthetic.main.main_activity_view.*
import java.util.*

class AlarmHomeActivity : AppCompatActivity() {

    private lateinit var listAdapter: AlarmRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_view)

        initList()
        fab_add.setOnClickListener { addAlarm() }
    }

    private fun initList() {
        val alarmList : MutableList<Alarm> = mutableListOf(
            Alarm("7:25", true, DayOfTheWeek(false, false , true, false, true, false, true), Date(2022, 10, 10, 1, 1), Mission.QR_CODE),
            Alarm("8:20", false, DayOfTheWeek(false, false , false, false, false, true, true), Date(2022, 10, 10, 1, 1), Mission.Steps),
            Alarm("6:15", true, DayOfTheWeek(true, true , true, true, true, false, false), Date(2022, 10, 10, 1, 1), Mission.QR_CODE),
            Alarm("17:15", true, DayOfTheWeek(true, true , true, true, true, true, true), Date(2022, 10, 10, 1, 1), Mission.Steps),
        )

        listAdapter = AlarmRecyclerViewAdapter(alarmList.toMutableList())
        alarm_list.adapter = listAdapter
        alarm_list.layoutManager = LinearLayoutManager(this)
    }

    private fun addAlarm() {
        val intent = Intent(this, AddAlarmActivity::class.java)

        startActivityForResult(intent, COMPOSE_REQUEST_CODE)
    }
    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }
}