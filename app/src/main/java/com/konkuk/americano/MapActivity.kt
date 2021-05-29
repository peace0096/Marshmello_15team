package com.konkuk.americano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.konkuk.americano.databinding.ActivityMapBinding
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {
    lateinit var binding:ActivityMapBinding
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMap()
        init()
    }



    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
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
}