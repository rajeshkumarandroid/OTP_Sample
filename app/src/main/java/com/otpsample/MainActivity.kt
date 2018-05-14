package com.otpsample

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest.permission
import android.Manifest.permission.RECEIVE_SMS
import android.Manifest.permission.READ_SMS
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.os.Build
import android.support.annotation.NonNull
import android.widget.Toast
import java.util.*
import android.content.IntentFilter
import android.view.View
import android.widget.Button


class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST = 1
    private var incomingSmsReceiver: IncomingSmsReceiver? = null
    var btn:Button?=null;
    private val permissions = arrayOf<String>(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        incomingSmsReceiver = IncomingSmsReceiver()
        btn = findViewById(R.id.button)
        btn!!.setOnClickListener({
           checkPermissions()
        })

    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {

                FetchResponse.getInstance().getResponse(generateOTP())
            } else {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST)
            }
        } else {

            FetchResponse.getInstance().getResponse(generateOTP())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.size <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                FetchResponse.getInstance().getResponse(generateOTP())
//                Toast.makeText(this, "Please Enable sms read and recieve permissions for auto OTP", Toast.LENGTH_LONG).show()
            } else {
                FetchResponse.getInstance().getResponse(generateOTP())
            }
        }
    }


    private fun generateOTP(): String {
        val len = 4
        val numbers = "0123456789"
        val random = Random()
        val otp = CharArray(len)
        for (i in 0 until len) {
            otp[i] = numbers[random.nextInt(numbers.length)]
        }
        return String(otp)
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(incomingSmsReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(incomingSmsReceiver)
    }
}
