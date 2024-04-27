package com.example.testandroidstudioproject

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.callbackFlow

class MainActivity2 : AppCompatActivity() {

    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null
    private var cameraDevice: CameraDevice? = null
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private var isFlashLight: Boolean = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationTextView: TextView





    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val activity2ToActivity1 = findViewById<Button>(R.id.button2)
        activity2ToActivity1.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val activity2ToActivity3 = findViewById<Button>(R.id.button3)
        activity2ToActivity3.setOnClickListener{
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
        findCam()
        val flashLightButton = findViewById<Button>(R.id.flashlight)
        flashLightButton.setOnClickListener {
            toggleFlashLight()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val coordsButton = findViewById<Button>(R.id.button9)
        locationTextView = findViewById(R.id.textView4)
        coordsButton.setOnClickListener {
            getLastLocation()
        }
    }


    private fun toggleFlashLight(){
        if (isFlashLight){
            turnOffFlashLight()
        }
        else{
            turnOnFlashLight()
        }
    }

    private fun turnOnFlashLight(){
        cameraId?.let {
            cameraManager.setTorchMode(it, true)
            isFlashLight = true
        }
    }

    private fun turnOffFlashLight(){
        cameraId?.let {
            cameraManager.setTorchMode(it, false)
            isFlashLight= false
        }
    }

    private fun findCam() {
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            for (id in cameraManager.cameraIdList) {
                val characteristics = cameraManager.getCameraCharacteristics(id)
                val available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
                val lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)
                if (available != null && available && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                    cameraId = id
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    locationTextView.text = "Latitude: $latitude, Longitude: $longitude"
                }
            }
            .addOnFailureListener { e ->
                locationTextView.text = "Error getting location: ${e.message}"
            }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
