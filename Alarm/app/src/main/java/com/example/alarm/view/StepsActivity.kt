package com.example.alarm.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.alarm.R
import com.example.alarm.utils.AudioPlay
import kotlinx.android.synthetic.main.qr.*

class StepsActivity : AppCompatActivity(), SensorEventListener {
    private var isSoundOn = true
    private var numberOfCycles = 0
    private lateinit var countDownTimer: CountDownTimer

    private var sensorManager: SensorManager? = null

    private var running = false

    private var totalSteps = 0f

    val ACTIVITY_RECOGNITION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.steps)

        setVolumeIcon()
        setProgressBar()

        if (isPermissionGranted()) {
            requestPermission()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {

        super.onResume()
        running = true

        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        if (stepSensor == null) {
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        // unregister listener
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        var tv_stepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)

        if (running) {
            totalSteps += event!!.values[0]
            countDownTimer.cancel()
            countDownTimer.start()
            val currentSteps = totalSteps.toInt()
            tv_stepsTaken.text = ("$currentSteps")
            if (currentSteps == 30) {
                AudioPlay.stopAudio()
                val intent = Intent(this@StepsActivity, MissionPassed::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                ACTIVITY_RECOGNITION_REQUEST_CODE
            )
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACTIVITY_RECOGNITION
        ) != PackageManager.PERMISSION_GRANTED
    }

    //handle requested permission result(allow or deny)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACTIVITY_RECOGNITION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                }
            }
        }
    }
    private fun setVolumeIcon() {
        sound_image.setOnClickListener {
            if (numberOfCycles >= 3 && isSoundOn) {
                Toast.makeText(
                    this@StepsActivity,
                    "Cannot mute alarm anymore!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (isSoundOn) {
                sound_image.setImageResource(R.drawable.ic_baseline_volume_off_24)
                isSoundOn = false
                AudioPlay.muteAudio()
            } else {
                sound_image.setImageResource(R.drawable.ic_baseline_volume_up_24)
                isSoundOn = true
                AudioPlay.unmuteAudio()
            }
        }
    }

    private fun setProgressBar() {
        progressBar.progressDrawable.setColorFilter(
            Color.LTGRAY, android.graphics.PorterDuff.Mode.SRC_IN)

        val maxValue: Int = 30000
        progressBar.max = maxValue
        progressBar.progress = maxValue
        countDownTimer  = object : CountDownTimer(maxValue.toLong(), 50) {
            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                numberOfCycles += 1
                sound_image.setImageResource(R.drawable.ic_baseline_volume_up_24)
                isSoundOn = true
                AudioPlay.unmuteAudio()
                progressBar.progress = maxValue
                start()
            }
        }.start()
    }
}
