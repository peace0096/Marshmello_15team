package com.konkuk.americano.UI.Map

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.konkuk.americano.UI.MyStore.MyStoreActivity
import com.konkuk.americano.R
import com.konkuk.americano.UI.Setting.SettingActivity
import com.konkuk.americano.ViewModel.UserViewModel
import com.konkuk.americano.databinding.ActivityMapBinding
import com.konkuk.americano.Model.StoreReviewData
import com.konkuk.americano.Repo.UserMe_Repo
import com.konkuk.americano.UI.CreateStore.CreateStoreActivity
import com.konkuk.americano.UI.MyReview.MyReviewActivity

import com.konkuk.americano.UI.StoreDetail.ReviewsAdapter
import com.konkuk.americano.UI.StoreDetail.StoreDetailActivity
import com.konkuk.americano.ViewModel.ReviewsViewModel

class MapActivity : AppCompatActivity() {
    lateinit var binding:ActivityMapBinding
    lateinit var googleMap: GoogleMap
    private val userViewModel = UserViewModel(this)
    private val reviewsViewModel = ReviewsViewModel()
    private lateinit var adapter:ReviewsAdapter

    var loc = LatLng(37.554752, 126.970631) //default
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
            googleMap.clear()
            val option1 = MarkerOptions()
            option1.position(loc)
            option1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            googleMap.addMarker(option1)
            for(e in it) {
                val option = MarkerOptions()
                val lng = LatLng(e.latitude, e.longitude)
                Log.d("store", e.title)
                Log.d("store", "${e.latitude}, ${e.longitude}")
                option.position(lng)
                option.title(e.title)
                option.snippet(e.content)
                option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                googleMap.addMarker(option)
                    .tag = e.storeId
            }

        })

        reviewsViewModel.recentReviewModel.observe(this, Observer {
            //setAdapter()
            val layout = LinearLayoutManager(this@MapActivity, LinearLayoutManager.VERTICAL, false)

            adapter = ReviewsAdapter(it, this@MapActivity, this@MapActivity)
            binding.apply {
                recyclerView.layoutManager = layout
                recyclerView.adapter = adapter
                if(reviewsViewModel != null) {
                    adapter.itemClickListener = object : ReviewsAdapter.OnItemClickListener {
                        override fun onItemClick(
                            holder: ReviewsAdapter.ViewHolder,
                            view: View,
                            data: StoreReviewData,
                            position: Int
                        ) {

                        }

                    }
                }

            }
        })

        userViewModel.usermodel.observe(this, Observer {
            var uri:Uri? = null
            if(it.profileImage.size != 0)
                uri = Uri.parse(it.profileImage[0])
            Log.d("mapactivity observe", uri.toString())
            binding.apply {
                val header_imageView = navigationView.getHeaderView(0).findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.imageView)
                val header_idTextView = navigationView.getHeaderView(0).findViewById<TextView>(R.id.idTextView)
                val header_nicknameTextView = navigationView.getHeaderView(0).findViewById<TextView>(R.id.nicknameTextView)
                if(uri != null)
                    Glide.with(baseContext)
                        .load(uri)
                        .into(header_imageView)
                header_idTextView.text = it.loginId
                header_nicknameTextView.text = it.nickname
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
                userViewModel.updateUserLocation(location.locations[location.locations.size-1].latitude, location.locations[location.locations.size-1].longitude)
                loc = LatLng(location.locations[location.locations.size-1].latitude, location.locations[location.locations.size-1].longitude)
                UserMe_Repo.getInstance().getModel().latitude = loc.latitude
                UserMe_Repo.getInstance().getModel().langitude = loc.longitude

                userViewModel.usermodel.value?.latitude = loc.latitude
                userViewModel.usermodel.value?.langitude = loc.longitude
                Log.d("user", userViewModel.usermodel.value?.latitude.toString())
                    if(!isMapLoad) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
                    drawMarkers()
                    isMapLoad = true
                }
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
            initMapAdapter()
        }
    }

    fun initMapAdapter() {
        googleMap.setInfoWindowAdapter(object:GoogleMap.InfoWindowAdapter {
            override fun getInfoContents(marker: com.google.android.gms.maps.model.Marker): View? {
                val marker_view = LayoutInflater.from(this@MapActivity).inflate(R.layout.store_marker, null)
                val marker_image = marker_view.findViewById<ImageView>(R.id.marker_image)
                val marker_title = marker_view.findViewById<TextView>(R.id.marker_title)
                val marker_content = marker_view.findViewById<TextView>(R.id.marker_content)

                val list = UserMe_Repo.getInstance().getStoreModel()
                for(e in list) {
                    if(marker.tag == e.storeId) {
                        marker_title.text = e.title
                        marker_content.text = e.content
                        Log.d("image", "${e.image.size}")
                        if(e.image.size != 0) {
                            var uri = Uri.parse(e.image[0])
                            Glide.with(this@MapActivity)
                                .load(uri)
                                .into(marker_image)
                        }
                    }
                }
                return marker_view
            }

            override fun getInfoWindow(p0: com.google.android.gms.maps.model.Marker): View? {
                return null
            }
        })
    }

    private fun initMapListener() {
        googleMap.setOnInfoWindowClickListener {
            val storeId = it.tag as Int
            val intent = Intent(this, StoreDetailActivity::class.java)
            intent.putExtra("storeId", storeId)
            startActivity(intent)
        }

        googleMap.setOnMapClickListener {
            Log.d("touch", "Click!")
        }
    }

    private fun init() {

        binding.apply {
            userViewModel.tokenmodel.value = UserMe_Repo.getInstance().gettoken()

            val bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            bottomSheetBehavior.halfExpandedRatio = 0.4f
            bottomSheetBehavior.isHideable = false
            bottomSheetBehavior.isFitToContents = false

            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)


            navigationView.setNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.store -> {
                        val intent = Intent(baseContext, MyStoreActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.review -> {
                        val intent = Intent(baseContext, MyReviewActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.setting -> {
                        val intent = Intent(baseContext, SettingActivity::class.java)
                        startActivity(intent)
                    }

                }
                drawerlayout.closeDrawers()
                false
            }

            updateLocation.setOnClickListener {
                reviewsViewModel.loadRecentReviews()
                drawMarkers()
                moveCameraMyLocation()
            }

            addLocation.setOnClickListener {
                val intent = Intent(this@MapActivity, CreateStoreActivity::class.java)
                startActivity(intent)
            }
        }
        reviewsViewModel.loadRecentReviews()
        userViewModel.getUserMe()

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
            if(!checkLocationSservicesStatus()) {   //gps ???????????????
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
        builder.setTitle("?????? ????????? ????????????")
        builder.setMessage(
            "?????? ???????????? ???????????? ?????? ???????????? ???????????????.\n ??????????????? ??????????????????????"
        )
        builder.setPositiveButton("??????", DialogInterface.OnClickListener{ dialog, id->
            val GpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(GpsSettingIntent, 1000)
        })
        builder.setNegativeButton("??????", DialogInterface.OnClickListener{ dialog, id->
            dialog.dismiss()

        })
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            1000-> {

                if(checkLocationSservicesStatus()) {
                    Toast.makeText(this, "GPS ????????? ?????????", Toast.LENGTH_SHORT).show()
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
