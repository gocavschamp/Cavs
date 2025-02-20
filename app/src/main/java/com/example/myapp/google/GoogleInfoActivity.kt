package com.example.myapp.google

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.moonlight.flyvideo.R
import com.nucarf.base.ui.BaseActivityWithTitle
import kotlinx.android.synthetic.main.activity_google.tvLocation

class GoogleInfoActivity : BaseActivityWithTitle() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google)
        titlelayout.setTitleText("locations")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun initData() {
        getLocation()

    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
            return
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    tvLocation.text = ("$latitude, $longitude")
                }
            }
            .addOnFailureListener {
                tvLocation.text = ("$0, $0")

            }

    }
}