package com.example.alarm

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarm.Domain.Alarm
import com.example.alarm.Domain.Mission
import com.example.alarm.database.AlarmStore
import com.example.alarm.database.RoomDatabase
import com.example.alarm.utils.AlarmReceiver
import com.example.alarm.view.AlarmRecyclerViewAdapter
import com.example.alarm.view.ShowAddUpdateAlarmActivity
import com.example.alarm.view_model.HomeViewModel
import com.example.alarm.view_model.HomeViewModelFactory
import kotlinx.android.synthetic.main.main_activity_view.*
import java.util.*
import java.util.Calendar.SECOND
import kotlin.math.roundToInt

class AlarmHomeActivity : AppCompatActivity() {


    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_view)

        fab_add.setOnClickListener { addAlarm() }
        val factory = HomeViewModelFactory(AlarmStore(RoomDatabase.getDb(this)))
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        initList()
//
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, AlarmReceiver::class.java)
//        val id = System.currentTimeMillis().toInt()
//        val pendingIntent1 = PendingIntent.getBroadcast(this,
//            id, intent,
//            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_MUTABLE)
//        val pendingIntent2 = PendingIntent.getBroadcast(this,
//            id + 1, intent,
//            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_MUTABLE)
//        val time1: Calendar = Calendar.getInstance()
//        val time2: Calendar = Calendar.getInstance()
//        time1.add(SECOND, 1)
//        time2.add(SECOND, 5)
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time1.timeInMillis, pendingIntent1)
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time2.timeInMillis, pendingIntent2)
//        Log.d("TAG", "$time1 loh")
//        Log.d("TAG", "$time2 loh")
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
                    alarm.days!!.Monday, alarm.days.Tuesday,
                    alarm.days.Wednesday, alarm.days.Thursday,
                    alarm.days.Friday, alarm.days.Saturday,
                    alarm.days.Sunday))
            putExtra(ShowAddUpdateAlarmActivity.ALARM_VOLUME, alarm.alarmVolume)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_MISSION, alarm.mission)
            putExtra(ShowAddUpdateAlarmActivity.STEP_COUNT, alarm.stepsCount)
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
            val alarmVolume = data.getFloatExtra(ShowAddUpdateAlarmActivity.ALARM_VOLUME, 0F).roundToInt()
            var QRCode = data.getStringExtra(ShowAddUpdateAlarmActivity.QR_CODE)



            var alarmId: Long = data.getLongExtra(ShowAddUpdateAlarmActivity.ALARM_ID, 0)
            if (alarmId != 0L) {
                viewModel.updateAlarm(alarmId, hour, minute, days, isActive, alarmVolume, Mission.None, 12, QRCode)
            } else {
                viewModel.addAlarm(hour, minute, days, alarmVolume, Mission.None, 123, QRCode)
            }
        }
    }


    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }
}