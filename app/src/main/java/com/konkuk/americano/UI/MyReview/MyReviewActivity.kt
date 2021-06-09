package com.konkuk.americano.UI.MyReview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.americano.Repo.Editstore_Repo
import com.konkuk.americano.UI.EditStore.EditStoreActivity
import com.konkuk.americano.UI.MyStore.MyStoreRecyclerAdapter
import com.konkuk.americano.ViewModel.MyReviewViewModel
import com.konkuk.americano.databinding.ActivityMyReviewBinding

class MyReviewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyReviewBinding
    private lateinit var adapter : MyReviewAdapter
    private lateinit var viewmodel : MyReviewViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewBinding.inflate(layoutInflater)
        viewmodel = MyReviewViewModel(this,this)
        setContentView(binding.root)

        init()
        initobserver()
    }


    override fun onResume() {
        super.onResume()
        viewmodel.callGetMyReviewAPI()
    }

    private fun init(){
        val layout = LinearLayoutManager(this@MyReviewActivity, LinearLayoutManager.VERTICAL,false)
        binding.apply {
            binding.myReviewListRecyclerview.layoutManager = layout

            myReviewListBackBtn.setOnClickListener {
                finish()
            }
        }
    }

    private fun initobserver(){
        viewmodel.reviewliveData.observe(this, Observer {

            adapter = MyReviewAdapter(viewmodel.reviewliveData)

            binding.myReviewListRecyclerview.adapter = adapter

        })

    }


}