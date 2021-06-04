package com.konkuk.americano.Map

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.konkuk.americano.MyStore.MyStoreActivity
import com.konkuk.americano.R
import com.konkuk.americano.SettingActivity
import com.konkuk.americano.ViewModel.UserViewModel
import com.konkuk.americano.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    lateinit var binding:ActivityMapBinding
    lateinit var googleMap: GoogleMap
    private val userViewModel = UserViewModel(this)

    var loc = LatLng(37.554752, 126.970631)
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var startupdate = false
    var isMapLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMap()
        initObserve()
        init()
    }

    fun initObserve() {
        userViewModel.listStoreModel.observe(this, Observer {
            for(e in it) {
                googleMap.clear()
                val option = MarkerOptions()
                val lng = LatLng(e.latitude, e.longitude)
                option.position(lng)
                option.title(e.title)
                option.snippet(e.content)
                option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                googleMap.addMarker(option)
            }

        })


    }


    fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }


        locationCallback = object: LocationCallback() {

            override fun onLocationResult(location: LocationResult) {
                if(location.locations.size == 0) return
                if(!isMapLoad) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
                    drawMarkers()
                    isMapLoad = true
                }
                userViewModel.updateUserLocation(location.locations[location.locations.size-1].latitude, location.locations[location.locations.size-1].longitude)
                loc = LatLng(location.locations[location.locations.size-1].latitude, location.locations[location.locations.size-1].longitude)

                Log.i("location", "LocationCallback() , ${loc.latitude}, ${loc.longitude}, ${location.locations.size-1}, $startupdate")
            }
        }
    }

    private fun drawMarkers() {
        userViewModel.getStoreListLoc(loc.latitude, loc.longitude)

    }

    private fun moveCameraMyLocation() {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
    }


    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            initLocation()
            initMapListener()
        }
    }

    private fun initMapListener() {
        googleMap.setOnMapClickListener {


        }
    }

    private fun init() {
        binding.apply {
            val bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            bottomSheetBehavior.halfExpandedRatio = 0.4f
            bottomSheetBehavior.isHideable = false
            bottomSheetBehavior.isFitToContents = false

            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

            navigationView.setNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.myinfo -> {

                    }
                    R.id.store -> {
                        val intent = Intent(baseContext, MyStoreActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.review -> {
//                        val intent = Intent(baseContext, MyStoreActivity::class.java)
//                        startActivity(intent)
                    }
                    R.id.setting -> {
                        val intent = Intent(baseContext, SettingActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.help -> {
                        val intent = Intent(baseContext, MyStoreActivity::class.java)
                        startActivity(intent)
                    }
                }
                drawerlayout.closeDrawers()
                false
            }

            updateLocation.setOnClickListener {
                drawMarkers()
                moveCameraMyLocation()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.apply {
            if(item!!.itemId == android.R.id.home) {
                drawerlayout.openDrawer(GravityCompat.START)
            }
        }



        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        binding.apply {
            if(drawerlayout.isDrawerOpen(GravityCompat.START)) {
                drawerlayout.closeDrawers()
            }
            else {
                super.onBackPressed()
            }
        }

    }

    fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100)

        }
        else {
            if(!checkLocationSservicesStatus()) {   //gps 켜져있는가
                showLocationServicesSetting()
            }
            else {
                startupdate = true
                initLocation()
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.getMainLooper()
                )

                drawMarkers()
                Log.i("location", "startLocationUpdates()")
            }
        }
    }

    fun checkLocationSservicesStatus() : Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }

    fun showLocationServicesSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n 위치설정을 허용하겠습니까?"
        )
        builder.setPositiveButton("설정", DialogInterface.OnClickListener{ dialog, id->
            val GpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(GpsSettingIntent, 1000)
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener{ dialog, id->
            dialog.dismiss()

        })
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            1000-> {

                if(checkLocationSservicesStatus()) {
                    Toast.makeText(this, "GPS 활성화 되었음", Toast.LENGTH_SHORT).show()
                    startLocationUpdates()
                }

            }
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        startupdate = false
        Log.i("location", "stopLocationUpdates()")
    }

    override fun onResume() {
        super.onResume()
        Log.i("location", "onResume()")
        if(!startupdate)
            startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        Log.i("location", "onPause()")
        stopLocationUpdates()
    }
}