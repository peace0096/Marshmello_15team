package com.konkuk.americano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)


        }
    }

}