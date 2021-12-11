package com.neobis.shoplistcleanarchitecture.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.neobis.shoplistcleanarchitecture.R
import com.neobis.shoplistcleanarchitecture.domain.ShopItem

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.shopList.observe(this){
            Log.d("MainAct", it.toString())
            shopListAdapter.shopList = it
        }

    }

    private fun setupRecyclerView() {
        val recView = findViewById<RecyclerView>(R.id.list_rv)
        with(recView){
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_DISABLED, ShopListAdapter.MAX_POOL_SIZE)
        }

    }


}