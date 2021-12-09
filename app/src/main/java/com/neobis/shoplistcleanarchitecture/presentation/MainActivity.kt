package com.neobis.shoplistcleanarchitecture.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.neobis.shoplistcleanarchitecture.R
import com.neobis.shoplistcleanarchitecture.domain.ShopItem

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListLL: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        shopListLL = findViewById(R.id.list_ll)

        viewModel.shopList.observe(this){
            Log.d("MainAct", it.toString())
            setShopList(it)
        }

    }

    private fun setShopList(items: List<ShopItem>) {
        shopListLL.removeAllViews()
        for(item in items){
            val layoutId = if(item.isActive){
                R.layout.shop_item_enabled
            }else{
                R.layout.shop_item_disabled
            }
            val view = LayoutInflater.from(this).inflate(layoutId, shopListLL, false)
            val nameTv = view.findViewById<TextView>(R.id.tv_name)
            val countTv = view.findViewById<TextView>(R.id.tv_count)
            countTv.text = item.counter.toString()
            nameTv.text = item.name
            view.setOnClickListener{
                viewModel.editShopItem(item)
                Log.d("MainAct", item.toString())
            }
            shopListLL.addView(view)
        }
    }



}