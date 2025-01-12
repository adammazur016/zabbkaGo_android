package com.adayup.zabbkago

import com.adayup.zabbkago.responsesDataClasses.Place
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.adayup.zabbkago.apiFunctions.getPlacesApiCall
import com.adayup.zabbkago.apiFunctions.getUserDetailsApiCall
import com.adayup.zabbkago.apiFunctions.makeVisitApiCall
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

import kotlinx.coroutines.launch
import sharedKeys
import kotlin.math.sqrt

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback


    lateinit var sharedPreferences: SharedPreferences
    val keys = sharedKeys()
    //stores the places to add markers at
    var globalPoints: List<Place> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        //init of sharedpreferences
        sharedPreferences = getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
        val userID = sharedPreferences.getString(keys.USER_ID_KEY, "null")

        //fusedLocationProviderClient gets user location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (userID != null){
            lifecycleScope.launch {
                val userDetails = getUserDetailsApiCall(applicationContext, userID)
                Log.d("DEBUG", userDetails.toString())
            }
        }

        //gets user location every 10 seconds
        locationRequest = LocationRequest.create().apply {
            interval = 1000 // Request location update every 10 seconds
            fastestInterval = 500 // The fastest interval for location updates, 5 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        //update the location after obtained
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    currentLocation = location
                    lifecycleScope.launch {
                        //update only the user info
                        updateMapLocation(currentLocation)
                    }
                }
            }
        }

        getCurrentLocationUser()

        val profileBtn: ImageView = findViewById<ImageView>(R.id.profile)
        profileBtn.setOnClickListener {
            val i = Intent(this@MapsActivity, StatisticsActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun addMarker(place : Place){
        val test1 = LatLng(place.lat, place.long)
        mMap.addMarker(MarkerOptions().position(test1).title(place.id.toString()).icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon50)))
    }

    private fun updateMapLocation(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        mMap.clear()
        mMap.setMinZoomPreference(15f)
        mMap.setMaxZoomPreference(18f)

        mMap.addMarker(MarkerOptions().position(latLng).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.test50)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
        mMap.addCircle(CircleOptions().center(latLng).strokeColor(Color.argb(220, 4, 237, 0)).radius(30.0).fillColor(Color.argb(80,194, 250, 192)))
        for(point in globalPoints){
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
            val context = applicationContext
            var points = getPlacesApiCall(context)

            globalPoints = points

            updateMapLocation(currentLocation)
        }
        val rank = sharedPreferences.getString(keys.RANK_KEY, null).toString()
        val rankView = findViewById<TextView>(R.id.rankView)
        rankView.setText("Rank points: " + rank);
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
        lateinit var markerTitle: String

        markerTitle = myMarker.title.toString()

        //If user clicked on himself, don't do anything
        if(myMarker.title == "Current Location"){
            return true
        }

        //Calculate the vector disctance between user and the marker
        val vectorLength: Double = getVectorLength(currentLocation.latitude, myMarker.position.latitude, currentLocation.longitude, myMarker.position.longitude)
        Log.d("VECTOR LENGTH", vectorLength.toString())

        //If the marker is in the green circle, add the ponit to visits
        if(vectorLength < 3.6848094149922953E-4){
            Log.d("CLICKED", markerTitle)
            lifecycleScope.launch {
                val response = makeVisitApiCall(applicationContext, markerTitle)

                if (response.message == "visit_done"){
                    Toast.makeText(applicationContext, "Putting the point to your account", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(applicationContext, "Please come back in next day", Toast.LENGTH_LONG).show()
                    Log.d("CLICKED", "something went wrong")
                }
            }
        } else {
            Toast.makeText(applicationContext, "Get closer to that point", Toast.LENGTH_LONG).show()
        }

        return true
    }


}