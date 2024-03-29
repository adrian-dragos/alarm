package com.example.alarm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarm.Domain.Alarm
import com.example.alarm.Domain.Mission
import com.example.alarm.R
import kotlinx.android.synthetic.main.alarm_card.view.*

class AlarmRecyclerViewAdapter(
    private val alarmList: MutableList<Alarm>,
    private val onDelete: (Long) -> (Unit),
    private val onUpdateIsActive: (Alarm) -> (Unit),
    private val onCardClick: (Alarm) -> Unit
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
        init {
            itemView.fab_delete.setOnClickListener { onDelete(alarmList[adapterPosition].id)}
            itemView.fab_switch.setOnClickListener { onUpdateIsActive(alarmList[adapterPosition]) }
            itemView.card_alarm.setOnClickListener { onCardClick(alarmList[adapterPosition]) }
        }

        fun bind(alarm: Alarm) {
            itemView.alarm_time.text = String.format("%02d:%02d", alarm.hour, alarm.minute)
            itemView.days.text = daysWhenAlarmIsActive(alarm)
            itemView.fab_switch.isChecked = alarm.isAlarmActive
            if (alarm.mission == Mission.QR_CODE)
                itemView.mission.setImageResource(R.drawable.ic_baseline_qr_code_24)
            else if (alarm.mission == Mission.Steps)
                itemView.mission.setImageResource(R.drawable.steps)
            else if (alarm.mission == Mission.None)
                itemView.mission.setImageResource(R.drawable.ic_baseline_access_alarms_24)
        }
    }

    private fun daysWhenAlarmIsActive(alarm: Alarm): String {
        if (alarm.days!!.Monday && alarm.days.Tuesday && alarm.days.Wednesday
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
            builder.append("Thu")
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