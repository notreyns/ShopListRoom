package com.neobis.shoplistcleanarchitecture.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neobis.shoplistcleanarchitecture.data.ShopListRepositoryImpl
import com.neobis.shoplistcleanarchitecture.domain.AddItemUseCase
import com.neobis.shoplistcleanarchitecture.domain.EditItemUseCase
import com.neobis.shoplistcleanarchitecture.domain.GetItemByIdUseCase
import com.neobis.shoplistcleanarchitecture.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val addItemUseCase = AddItemUseCase(repository)
    private val editItemUseCase = EditItemUseCase(repository)
    private val getItemUseCase = GetItemByIdUseCase(repository)

    private var _errorInputName = MutableLiveData<Boolean>()
    val errorInputName : LiveData<Boolean>
        get() = _errorInputName

    private var _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount : LiveData<Boolean>
        get() = _errorInputCount

    private var _shouldCloseScreen= MutableLiveData<Boolean>()
    val shouldCloseScreen : LiveData<Boolean>
        get() = _shouldCloseScreen

    private var _shopItem= MutableLiveData<ShopItem>()
    val shopItem : LiveData<ShopItem>
        get() = _shopItem

    fun addItem(inputName: String? , inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldValid = validateInput(name, count)
        if(fieldValid){
            addItemUseCase.addItem(ShopItem(name,true, count))
            finishWork()
        }
    }

    fun getItem(id: Int){
        getItemUseCase.getItemById(id)
    }

    fun editItem(inputName: String? , inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldValid = validateInput(name, count)
        if(fieldValid){
            _shopItem.value?.let {
                val item =it.copy(name = name, counter = count)
                editItemUseCase.editItem(item)
                finishWork()
            }

        }
    }

    private fun parseName(inputName: String?): String{
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        }catch (e: Exception){
            0
        }
    }
    private fun validateInput(name: String, count: Int): Boolean{
        var result = true
        if(name.isBlank()){
            _errorInputName.value = true
            result = false
        }
        if(count <= 0 ){
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName(){
        _errorInputName.value = false
    }

    fun resetErrorInputCount(){
        _errorInputCount.value = false
    }
    private fun finishWork(){
        _shouldCloseScreen.value = true
    }
}