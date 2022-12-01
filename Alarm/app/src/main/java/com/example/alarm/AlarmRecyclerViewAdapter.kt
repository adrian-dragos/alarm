package com.example.alarm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alarm.Domain.Alarm
import kotlinx.android.synthetic.main.alarm_card.view.*

class AlarmRecyclerViewAdapter(
    private val alarmList: MutableList<Alarm>,
//    private val onClick: (Alarm) -> (Unit)
)
: RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarm_card, parent, false)
        return AlarmViewHolder(view);
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(alarmList[position])
    }

    override fun getItemCount(): Int {
        return alarmList.size;
    }

    inner class AlarmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init { }

        fun bind(alarm: Alarm) {
            itemView.txv_content.text = alarm.description
            itemView.days.text = daysWhenAlarmIsActive(alarm)
            itemView.is_active.isChecked = alarm.isAlarmActive
        }
    }

    private fun daysWhenAlarmIsActive(alarm: Alarm): String {
        if (alarm.days.Monday && alarm.days.Tuesday && alarm.days.Wednesday
            && alarm.days.Thursday && alarm.days.Friday && alarm.days.Saturday
            && alarm.days.Sunday) {
            return "Every Day!"
        }
        val builder = StringBuilder()
        if (alarm.days.Monday) builder.append("Mo");

        if (alarm.days.Tuesday) {
            if (!builder.isNullOrEmpty()) builder.append(", ")
            builder.append("Tue")
        }
        if (alarm.days.Wednesday) {
            if (!builder.isNullOrEmpty()) builder.append(", ")
            builder.append("Wed")
        }
        if (alarm.days.Thursday) {
            if (!builder.isNullOrEmpty()) builder.append(", ")
            builder.append("Thy")
        }
        if (alarm.days.Friday) {
            if (!builder.isNullOrEmpty()) builder.append(", ")
            builder.append("Fri")
        }
        if (alarm.days.Saturday) {
            if (!builder.isNullOrEmpty()) builder.append(", ")
            builder.append("Sat")
        }
        if (alarm.days.Sunday) {
            if (!builder.isNullOrEmpty()) builder.append(", ")
            builder.append("Sun")
        }
        return builder.toString();
    }
}