package com.adayup.zabbkago

import Place
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import getPlacesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.math.sqrt

class maps : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    private lateinit var currentLocation: Location

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback


    lateinit var sharedPreferences: SharedPreferences
    val API_KEY = "api_key"
    var PREFS_KEY = "prefs"
    suspend fun makeApiCall(): List<Place>{
        val service = RetrofitClient.retrofitInstance.create(getPlacesApiService::class.java)
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        val apiKey = sharedPreferences.getString(API_KEY, null).toString()
        val response: Response<List<Place>> = withContext(Dispatchers.IO) {
            service.GetPlaces(apiKey)
        }
        if (response.isSuccessful) {
            val res = response.body()
            res?.let {

                return it
            }
        }


        return emptyList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create().apply {
            interval = 1000 // Request location update every 10 seconds
            fastestInterval = 500 // The fastest interval for location updates, 5 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    // Handle the new location update here
                    currentLocation = location
                    lifecycleScope.launch {

                        //update only the user info
                        updateMapLocation(currentLocation)
                    } // You'll implement this method next
                }
            }
        }

        getCurrentLocationUser()
    }

    private fun addMarker(place : Place){
        val test1 = LatLng(place.latitude, place.longitude)
        mMap.addMarker(MarkerOptions().position(test1).title("test marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon50)))
    }

    var punkty: List<Place> = emptyList()
    private fun updateMapLocation(location: Location, points: List<Place>) {
        val latLng = LatLng(location.latitude, location.longitude)
        mMap.clear() // Clear the previous location marker

        mMap.addMarker(MarkerOptions().position(latLng).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.test50)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
        mMap.addCircle(CircleOptions().center(latLng).strokeColor(Color.argb(220, 4, 237, 0)).radius(30.0).fillColor(Color.argb(80,194, 250, 192)))

        punkty = points

        for(point in points){
            addMarker(point)
        }
    }

    private fun updateMapLocation(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        mMap.clear()
        mMap.setMinZoomPreference(15f)
        mMap.setMaxZoomPreference(18f)
        // Clear the previous location marker

        mMap.addMarker(MarkerOptions().position(latLng).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.test50)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.addCircle(CircleOptions().center(latLng).strokeColor(Color.argb(220, 4, 237, 0)).radius(30.0).fillColor(Color.argb(80,194, 250, 192)))

        for(point in punkty){
            addMarker(point)
        }
    }

    private fun getVectorLength(x1: Double, x2: Double, y1: Double, y2: Double): Double{
        val x12 = x2-x1
        val y12 = y2-y1

        return sqrt( x12*x12 + y12*y12 )
    }

    private fun getCurrentLocationUser() {
        if(ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }

        val getLocation = fusedLocationProviderClient.lastLocation.addOnSuccessListener {

                location ->

            if(location != null){
                currentLocation = location

                val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
                mapFragment?.getMapAsync(this)

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){

            permissionCode -> if(grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocationUser()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // SETTING THE LOCATION TO ADD TO MAP TO USER LOCATION
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)
        lifecycleScope.launch {
            var points = makeApiCall()
            updateMapLocation(currentLocation, points)
        }


    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onMarkerClick(myMarker: Marker): Boolean {
        val vectorLength: Double = getVectorLength(currentLocation.latitude, myMarker.position.latitude, currentLocation.longitude, myMarker.position.longitude)

        var toastText: String
        if(vectorLength >= 3.5848094149922953E-4){
            toastText = "Come closer to that point!"
        } else {
            toastText = "Point collected!"
        }

        Toast.makeText(applicationContext, "CLICKED ON MARKER: " + myMarker.title + " - " + toastText, Toast.LENGTH_LONG).show()
        return true
    }


}