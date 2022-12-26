package com.example.alarm.view

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.alarm.R
import com.example.alarm.databinding.QrBinding
import com.example.alarm.utils.AudioPlay
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.add_alarm_view.*
import kotlinx.android.synthetic.main.qr.*
import kotlinx.android.synthetic.main.qr.sound_image
import java.io.IOException

class QRActivity : AppCompatActivity() {
    private var isSoundOn = true

    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private lateinit var binding: QrBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = QrBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setVolumeIcon()

        progressBar.progressDrawable.setColorFilter(
            Color.LTGRAY, android.graphics.PorterDuff.Mode.SRC_IN)

        progressBar.progress = 30000
        object : CountDownTimer(30000, 50) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("TAG", "millisUntilFinished = $millisUntilFinished")
                progressBar.progress = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                progressBar.progress = 30000
            }
        }.start()

        if (ContextCompat.checkSelfPermission(
                this@QRActivity, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }

        val aniSlide: Animation =
            AnimationUtils.loadAnimation(this@QRActivity, R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)

    }

    private fun setVolumeIcon() {
        sound_image.setOnClickListener {
            if (isSoundOn) {
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


    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()

        binding.cameraSurfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    //Start preview after 1s delay
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

            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue

                    runOnUiThread {
                        cameraSource.stop()
                        if (scannedValue == "http://en.m.wikipedia.org") {
                            Toast.makeText(
                                this@QRActivity,
                                "value- $scannedValue",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                        Toast.makeText(this@QRActivity, "Wrong QR Code", Toast.LENGTH_SHORT).show()

                    }
                } else {
                    Toast.makeText(this@QRActivity, "value- else", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@QRActivity,
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
        AudioPlay.stopAudio()
        cameraSource.stop()
    }
}