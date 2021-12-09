package com.neobis.shoplistcleanarchitecture.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GetShopListUseCase(private val repository: ShopListRepository) {
    fun getShopList(): LiveData<List<ShopItem>> {
        return repository.getShopList()
    }
}