package com.konkuk.americano.UI.MyStore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.americano.UI.EditStore.EditStoreActivity
import com.konkuk.americano.Repo.Editstore_Repo
import com.konkuk.americano.ViewModel.MyStoreViewModel
import com.konkuk.americano.databinding.ActivityMyStoreBinding

class MyStoreActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMyStoreBinding
    private lateinit var adapter : MyStoreRecyclerAdapter
    private lateinit var viewmodel : MyStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyStoreBinding.inflate(layoutInflater)
        viewmodel = MyStoreViewModel(this,this)
        setContentView(binding.root)

        init()
        initobserver()

    }

    override fun onResume() {
        super.onResume()
        viewmodel.callGetMystoreAPI()
    }


    private fun init(){
        val layout = LinearLayoutManager(this@MyStoreActivity,LinearLayoutManager.VERTICAL,false)
        binding.apply {
            binding.myStoreListRecyclerview.layoutManager = layout

            myStoreListBackBtn.setOnClickListener {
                finish()
            }

        }

    }


    private fun initobserver(){
        viewmodel.storeliveData.observe(this, Observer {

            adapter = MyStoreRecyclerAdapter(viewmodel.storeliveData)
            adapter.itemClickListener = object : MyStoreRecyclerAdapter.onItemClickListener {
                override fun onItemClick(view: View, position: Int) {

                    val intent = Intent(this@MyStoreActivity, EditStoreActivity::class.java)
                    Editstore_Repo.getInstance().setStoreId(adapter.data.value!![position].storeId)
                    startActivity(intent)
                }
            }
            binding.myStoreListRecyclerview.adapter = adapter

        })

    }





}