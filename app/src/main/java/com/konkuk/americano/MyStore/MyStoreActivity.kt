package com.konkuk.americano.MyStore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.americano.EditStore.EditStoreActivity
import com.konkuk.americano.R
import com.konkuk.americano.databinding.ActivityMyStoreBinding

class MyStoreActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMyStoreBinding
    private lateinit var adapter : MyStoreRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()


    }


    private fun init(){
        val layout = LinearLayoutManager(this@MyStoreActivity,LinearLayoutManager.VERTICAL,false)
        binding.apply {
            binding.myStoreListRecyclerview.layoutManager = layout

            var tempdata = ArrayList<MyStoreData>()
            tempdata.add(MyStoreData("","test1","test1-1" ))
            tempdata.add(MyStoreData("","test2","test2-1" ))
            tempdata.add(MyStoreData("","test3","test3-1" ))
            tempdata.add(MyStoreData("","test4","test4-1" ))
            tempdata.add(MyStoreData("","test5","test5-1" ))

            adapter = MyStoreRecyclerAdapter(tempdata)
            adapter.itemClickListener = object : MyStoreRecyclerAdapter.onItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val intent = Intent(this@MyStoreActivity,EditStoreActivity::class.java)
                    startActivity(intent)
                }
            }

            binding.myStoreListRecyclerview.adapter = adapter

            myStoreListBackBtn.setOnClickListener {
                finish()
            }

        }

    }



}