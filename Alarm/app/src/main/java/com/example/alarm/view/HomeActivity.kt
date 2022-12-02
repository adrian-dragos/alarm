package com.example.alarm

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarm.Domain.Alarm
import com.example.alarm.database.AlarmStore
import com.example.alarm.database.RoomDatabase
import com.example.alarm.view.ShowAddUpdateAlarmActivity
import com.example.alarm.view.AlarmRecyclerViewAdapter
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
        alarm_list.apply {
            layoutManager = LinearLayoutManager(this@AlarmHomeActivity)
        }

        viewModel.alarmsLiveData.observe(this, Observer { alarms ->
            alarm_list.adapter = AlarmRecyclerViewAdapter(
                alarms.toMutableList(),
                { deleteAlarm(it) },
                { updateIsAlarmActive(it.id, !it.isAlarmActive)},
                { showAlarm(it)})
        })

        viewModel.retrieveAlarms()
    }

    private fun addAlarm() {
        val intent = Intent(this, ShowAddUpdateAlarmActivity::class.java)

        startActivityForResult(intent, COMPOSE_REQUEST_CODE)
    }

    private fun showAlarm(alarm: Alarm) {

        val intent = Intent(this, ShowAddUpdateAlarmActivity::class.java).apply {
            putExtra(ShowAddUpdateAlarmActivity.ALARM_ID, alarm.id)
            putExtra(ShowAddUpdateAlarmActivity.IS_ALARM_ACTIVE, alarm.isAlarmActive)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_HOUR, alarm.hour)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_MINUTE, alarm.minute)
            putExtra(
                ShowAddUpdateAlarmActivity.ALARM_DAYS, booleanArrayOf(
                    alarm.days!!.Monday,
                    alarm.days.Tuesday,
                    alarm.days.Wednesday,
                    alarm.days.Thursday,
                    alarm.days.Friday,
                    alarm.days.Saturday,
                    alarm.days.Sunday
                ))
        }
        startActivityForResult(intent, COMPOSE_REQUEST_CODE)
    }

    private fun deleteAlarm(id: Long) {
        viewModel.removeAlarm(id)
    }

    private fun updateIsAlarmActive(id: Long, isActive: Boolean) {
        viewModel.updateIsAlarmActive(id, isActive)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            COMPOSE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) extractAlarm(data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun extractAlarm(data: Intent?) {
        data?.let {
            val hour = data.getIntExtra(ShowAddUpdateAlarmActivity.ALARM_HOUR, 0)
            val minute = data.getIntExtra(ShowAddUpdateAlarmActivity.ALARM_MINUTE, 0)
            val days = data.getBooleanArrayExtra(ShowAddUpdateAlarmActivity.ALARM_DAYS)
            val isActive = data.getBooleanExtra(ShowAddUpdateAlarmActivity.IS_ALARM_ACTIVE, false)

            var alarmId: Long = data.getLongExtra(ShowAddUpdateAlarmActivity.ALARM_ID, 0)
            if (alarmId != 0L) {
                viewModel.updateAlarm(alarmId, hour, minute, days, isActive)
            } else {
                viewModel.addAlarm(hour, minute, days)
            }
        }
    }

    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }
}