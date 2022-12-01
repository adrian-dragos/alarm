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
       // TODO("Not yet implemented")
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
        }
    }
}