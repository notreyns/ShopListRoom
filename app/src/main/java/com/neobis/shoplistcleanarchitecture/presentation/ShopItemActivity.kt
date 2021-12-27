package com.neobis.shoplistcleanarchitecture.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neobis.shoplistcleanarchitecture.R
import com.neobis.shoplistcleanarchitecture.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseModeIntent()
        launchRightScreen()
    }
    private fun launchRightScreen() {
       val fragment =  when (screenMode) {
            MODE_EDIT -> newInstanceEditFragment(shopItemId)
            MODE_ADD -> newInstanceAddFragment()
           else -> throw RuntimeException("Mode is unknown")
       }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }


    private fun parseModeIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Mode is unknown")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ID)) {
                throw RuntimeException("Param shop id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ID, ShopItem.UNDEFINED_ID)
        }
    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ID = "extra_shop_item_id"
        private const val UNKNOWN_MODE = ""

        fun newInstanceAddFragment(): ShopItemFragment{
            return ShopItemFragment().apply { 
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }
        fun newInstanceEditFragment(shopItemId: Int): ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ID, shopItemId)
                }
            }
        }
        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ID, shopItemId)
            return intent
        }
    }

}