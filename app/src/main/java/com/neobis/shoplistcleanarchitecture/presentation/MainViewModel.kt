package com.neobis.shoplistcleanarchitecture.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neobis.shoplistcleanarchitecture.data.ShopListRepositoryImpl
import com.neobis.shoplistcleanarchitecture.domain.DeleteItemUseCase
import com.neobis.shoplistcleanarchitecture.domain.EditItemUseCase
import com.neobis.shoplistcleanarchitecture.domain.GetShopListUseCase
import com.neobis.shoplistcleanarchitecture.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditItemUseCase(repository)
    private val deleteItemUseCase = DeleteItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun editShopItem(shopItem: ShopItem) {
        scope.launch {
            val newItem = shopItem.copy(isActive = !shopItem.isActive)
            editShopItemUseCase.editItem(newItem)
        }
    }

    fun deleteShopItem(shopItem: ShopItem) {
        scope.launch {
            deleteItemUseCase.deleteItem(shopItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }


}