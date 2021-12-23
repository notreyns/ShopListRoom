package com.neobis.shoplistcleanarchitecture.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.neobis.shoplistcleanarchitecture.R
import com.neobis.shoplistcleanarchitecture.domain.ShopItem


class ShopItemFragment(
    private var screenMode: String = UNKNOWN_MODE,
    private var shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {
    private lateinit var viewModel: ShopItemViewModel


    private lateinit var til_name: TextInputLayout
    private lateinit var til_count: TextInputLayout
    private lateinit var et_name: EditText
    private lateinit var et_count: EditText
    private lateinit var saveButton: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseMode()
        initViews(view)
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
        viewModel.shopItem.observe(viewLifecycleOwner) {
            et_name.setText(it.name)
            et_count.setText(it.counter.toString())
        }

        saveButton.setOnClickListener {
            viewModel.editItem(et_name.text?.toString(), et_count.text?.toString())
        }
    }

    private fun showInputError() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                til_name.error = "Ошибка ввода имени"
            } else {
                til_name.error = null
            }
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                til_count.error = "Ошибка ввода количества"
            } else {
                til_count.error = null
            }
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            if (it) activity?.onBackPressed()
        }
    }

    private fun initViews(view: View) {
        til_name = view.findViewById(R.id.name_til)
        til_count = view.findViewById(R.id.count_til)
        et_name = view.findViewById(R.id.et_name)
        et_count = view.findViewById(R.id.et_count)
        saveButton = view.findViewById(R.id.save_btn)

    }

    private fun viewModelObservers() {
        et_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        et_count.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun parseMode() {
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
            throw RuntimeException("Mode is unknown")
        }
        if (screenMode == MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID) {
            throw RuntimeException("Param shop id is absent")
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