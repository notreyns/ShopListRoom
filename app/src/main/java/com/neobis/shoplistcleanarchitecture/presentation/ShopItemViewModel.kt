package com.neobis.shoplistcleanarchitecture.presentation

import android.app.Application
import androidx.lifecycle.*
import com.neobis.shoplistcleanarchitecture.data.ShopListRepositoryImpl
import com.neobis.shoplistcleanarchitecture.domain.AddItemUseCase
import com.neobis.shoplistcleanarchitecture.domain.EditItemUseCase
import com.neobis.shoplistcleanarchitecture.domain.GetItemByIdUseCase
import com.neobis.shoplistcleanarchitecture.domain.ShopItem
import kotlinx.coroutines.*
import java.lang.Exception

class ShopItemViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

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
            viewModelScope.launch {
                addItemUseCase.addItem(ShopItem(name,true, count))
                finishWork()
            }

        }
    }

    fun getItem(id: Int){
        viewModelScope.launch {
            val item =getItemUseCase.getItemById(id)
            _shopItem.value = item
        }

    }

    fun editItem(inputName: String? , inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldValid = validateInput(name, count)
        if(fieldValid){
            _shopItem.value?.let {
                viewModelScope.launch {
                    val item =it.copy(name = name, counter = count)
                    editItemUseCase.editItem(item)
                    finishWork()
                }
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