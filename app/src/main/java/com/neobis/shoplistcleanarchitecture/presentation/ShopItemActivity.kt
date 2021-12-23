package com.neobis.shoplistcleanarchitecture.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.neobis.shoplistcleanarchitecture.R
import com.neobis.shoplistcleanarchitecture.domain.ShopItem
import java.lang.RuntimeException

class ShopItemActivity : AppCompatActivity() {
    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID
    private lateinit var til_name: TextInputLayout
    private lateinit var til_count: TextInputLayout
    private lateinit var et_name: EditText
    private lateinit var et_count: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseModeIntent()
        initViews()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        launchRightScreen()
        showInputError()
        viewModelObservers()

    }

    private fun launchRightScreen() {
        when (screenMode) {
            MODE_EDIT -> launchEditScreen()
            MODE_ADD -> launchAddScreen()
        }
    }

    private fun launchAddScreen() {
        saveButton.setOnClickListener {
            viewModel.addItem(et_name.text.toString(), et_count.text.toString())
        }
    }

    private fun launchEditScreen() {
        viewModel.getItem(shopItemId)
        viewModel.shopItem.observe(this){
            et_name.setText( it.name)
            et_count.setText(it.counter.toString())
        }

        saveButton.setOnClickListener {
            viewModel.editItem(et_name.text?.toString(), et_count.text?.toString())
        }
    }

    private fun showInputError() {
        viewModel.errorInputName.observe(this){
            if(it){
                til_name.error = "Ошибка ввода имени"
            }else{
                til_name.error =null
            }
        }
        viewModel.errorInputCount.observe(this){
            if(it){
                til_count.error = "Ошибка ввода количества"
            }else{
                til_count.error =null
            }
        }
        viewModel.shouldCloseScreen.observe(this){
            if(it) finish()
        }
    }

    private fun initViews() {
        til_name = findViewById(R.id.name_til)
        til_count = findViewById(R.id.count_til)
        et_name = findViewById(R.id.et_name)
        et_count = findViewById(R.id.et_count)
        saveButton = findViewById(R.id.save_btn)

    }
    private fun viewModelObservers(){
        et_name.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        et_count.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
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