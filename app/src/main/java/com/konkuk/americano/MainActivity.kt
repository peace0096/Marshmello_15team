package com.konkuk.americano

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konkuk.americano.databinding.ActivityMainBinding
import com.konkuk.americano.ui.storedetail.StoreDetailActivity
import com.konkuk.americano.ui.writereview.WriteReviewActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            button.setOnClickListener {
                val intent = Intent(this@MainActivity, WriteReviewActivity::class.java)
                startActivity(intent)
            }
            button2.setOnClickListener {
                val intent = Intent(this@MainActivity, StoreDetailActivity::class.java)
                startActivity(intent)
            }
        }

    }
}