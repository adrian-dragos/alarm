package com.example.alarm.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
//        Log.d("TAG", "ajunge aici")
        val i = Intent()
        i.setClassName("com.example.alarm", "com.example.alarm.view.AlarmActivity")
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }
}