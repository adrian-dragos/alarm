package com.example.alarm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_alarm_view.*

class AddAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_alarm_view)

        save_alarm_button.setOnClickListener {
            val returnIntent = Intent().apply {
                val content = etx_content.editableText.toString()
                putExtra(EXTRA_TEXT, content)
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }

}