package com.example.alarm.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.R
import com.google.zxing.integration.android.IntentIntegrator

class QRActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr)

        val scanner = IntentIntegrator(this)
        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        scanner.initiateScan()
        scanner.setBeepEnabled(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents !=  null) {
                Log.d("TAG", "Scanned: " + result.contents)
                finishAffinity()
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}