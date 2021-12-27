package com.neobis.shoplistcleanarchitecture.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.neobis.shoplistcleanarchitecture.R
import com.neobis.shoplistcleanarchitecture.presentation.ShopItemActivity.Companion.newIntentAddItem
import com.neobis.shoplistcleanarchitecture.presentation.ShopItemActivity.Companion.newIntentEditItem

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private var shopItemContainer : FragmentContainerView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_container)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
        findViewById<FloatingActionButton>(R.id.add_item_btn).setOnClickListener{
            if (isOnePaneMode()) {
                startActivity(newIntentAddItem(this))
            } else {
                launchLandscapeModeFragment(ShopItemFragment.newInstanceAddFragment())
            }
        }

    }
    private fun isOnePaneMode(): Boolean{
        return shopItemContainer == null;
    }

    private fun launchLandscapeModeFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        val recView = findViewById<RecyclerView>(R.id.list_rv)
        with(recView) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupClickListener()
        setupOnLongClickListener()
        setupSwipeListener(recView)
    }

    private fun setupSwipeListener(recView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

        }

        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recView)
    }

    private fun setupOnLongClickListener() {
        shopListAdapter.onShopLongClickListener = {

            viewModel.editShopItem(it)
        }
    }

    private fun setupClickListener() {
        shopListAdapter.onShopClickListener = {
            if (isOnePaneMode()) {
                startActivity(newIntentEditItem(this, it.id))
            } else {
                launchLandscapeModeFragment(ShopItemFragment.newInstanceEditFragment(it.id))
            }
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

}


