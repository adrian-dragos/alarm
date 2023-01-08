package com.example.alarm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.alarm.Domain.Mission
import com.example.alarm.R
import com.example.alarm.databinding.ConfigureQrBinding
import com.example.alarm.utils.AudioPlay
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.configure_qr.*
import kotlinx.android.synthetic.main.configure_steps.*
import java.io.IOException

class ConfigureQRCodeMissionActivity: AppCompatActivity() {

    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private lateinit var binding: ConfigureQrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ConfigureQrBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.configure_qr)

        if (ContextCompat.checkSelfPermission(
                this@ConfigureQRCodeMissionActivity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }

        val aniSlide: Animation =
            AnimationUtils.loadAnimation(this@ConfigureQRCodeMissionActivity, R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)

    }


    private fun saveQR(qr_code: String) {

        var selectedAlarmId: Long = intent.getLongExtra(ShowAddUpdateAlarmActivity.ALARM_ID, 0)
        val hour = intent.getIntExtra(ShowAddUpdateAlarmActivity.ALARM_HOUR, 0)
        val minute = intent.getIntExtra(ShowAddUpdateAlarmActivity.ALARM_MINUTE, 0)
        val days = intent.getBooleanArrayExtra(ShowAddUpdateAlarmActivity.ALARM_DAYS)
        val isActive = intent.getBooleanExtra(ShowAddUpdateAlarmActivity.IS_ALARM_ACTIVE, false)
        val alarmVolume = intent.getFloatExtra(ShowAddUpdateAlarmActivity.ALARM_VOLUME, 0F)
        var QRCode = intent.getStringExtra(ShowAddUpdateAlarmActivity.QR_CODE)



        val intent = Intent(this, ShowAddUpdateAlarmActivity::class.java).apply {
            putExtra(ShowAddUpdateAlarmActivity.ALARM_ID, selectedAlarmId)
            putExtra(ShowAddUpdateAlarmActivity.IS_ALARM_ACTIVE, isActive)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_HOUR, hour)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_MINUTE, minute)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_DAYS, days)
            putExtra(ShowAddUpdateAlarmActivity.ALARM_VOLUME, alarmVolume.toInt())
            putExtra(ShowAddUpdateAlarmActivity.ALARM_MISSION, Mission.QR_CODE)
            putExtra(ShowAddUpdateAlarmActivity.QR_CODE, qr_code)
            putExtra(ShowAddUpdateAlarmActivity.IS_SETTING_MISSION, true)
            putExtra(ShowAddUpdateAlarmActivity.QR_CODE, qr_code)
        }
        startActivity(intent)
    }

    private fun setupControls() {
        barcodeDetector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1080, 1080)
            .setAutoFocusEnabled(true)
            .build()

        binding.cameraSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })


        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(applicationContext, "Scanner has been closed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue

                    runOnUiThread {
                        if (scannedValue != null) {
                            Toast.makeText(
                                this@ConfigureQRCodeMissionActivity,
                                "Code successfully added",
                                Toast.LENGTH_SHORT
                            ).show()
                            cameraSource.stop()
                            saveQR(scannedValue)
                        }
                    }
                }
            }
        })
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@ConfigureQRCodeMissionActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }
}