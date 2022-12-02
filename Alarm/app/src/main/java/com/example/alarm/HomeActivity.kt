package com.example.alarm

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarm.Domain.Alarm
import com.example.alarm.Domain.DayOfTheWeek
import com.example.alarm.Domain.Mission
import com.example.alarm.database.AlarmStore
import com.example.alarm.database.RoomDatabase
import com.example.alarm.view_model.HomeViewModel
import com.example.alarm.view_model.HomeViewModelFactory
import kotlinx.android.synthetic.main.main_activity_view.*

class AlarmHomeActivity : AppCompatActivity() {


    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_view)

        fab_add.setOnClickListener { addAlarm() }

        val factory = HomeViewModelFactory(AlarmStore(RoomDatabase.getDb(this)))
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        initList()

    }

    private fun initList() {
        val alarmList : MutableList<Alarm> = mutableListOf(
            Alarm(1,"-", true, DayOfTheWeek(false, false , true, false, true, false, true), 7, 15, Mission.QR_CODE),
            Alarm(2,"-", false, DayOfTheWeek(false, false , false, false, false, true, true), 5, 20, Mission.Steps),
            Alarm(3,"-", true, DayOfTheWeek(true, true , true, true, true, false, false), 6, 30, Mission.QR_CODE),
            Alarm(4,"-", true, DayOfTheWeek(true, true , true, true, true, true, true), 17, 20, Mission.Steps),
        )



//        listAdapter = AlarmRecyclerViewAdapter(alarmList.toMutableList())
//        alarm_list.adapter = listAdapter
//        alarm_list.layoutManager = LinearLayoutManager(this)

        alarm_list.apply {
            layoutManager = LinearLayoutManager(this@AlarmHomeActivity)
        }

        viewModel.alarmsLiveData.observe(this, Observer { alarms ->
            alarm_list.adapter = AlarmRecyclerViewAdapter(alarms.toMutableList())
        })

        viewModel.retrieveAlarms()
    }

    private fun addAlarm() {
        val intent = Intent(this, AddAlarmActivity::class.java)

        startActivityForResult(intent, COMPOSE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            COMPOSE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) extractAlarm(data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun extractAlarm(data: Intent?) {
        data?.let {
            val hour = data.getIntExtra(AddAlarmActivity.ALARM_HOUR, 0)
            val minute = data.getIntExtra(AddAlarmActivity.ALARM_MINUTE, 0)
            viewModel.addAlarm(hour, minute)
        }
    }

    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }
}