package com.example.alarm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.R
import kotlinx.android.synthetic.main.configure_steps.*

class ConfigureStepMissionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configure_steps)

        configureNumberPicker()


    }

    private fun configureNumberPicker() {
        number_picker.minValue = 0
        number_picker.maxValue = 999
        number_picker.value = 3
        number_picker.wrapSelectorWheel = false;

        val list = mutableListOf<String>("10")
        for (i in 20..10000 step 10)
            list.add(i.toString())
        number_picker.displayedValues = list.toTypedArray()

    }

}