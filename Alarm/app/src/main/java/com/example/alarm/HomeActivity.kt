package com.example.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarm.Domain.Alarm
import kotlinx.android.synthetic.main.main_activity_view.*

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
            Alarm("7:25"),
            Alarm("8:40"),
            Alarm("6:55")
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