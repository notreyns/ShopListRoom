package com.neobis.shoplistcleanarchitecture.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend fun addItem(shopItem: ShopItem)

    suspend fun deleteItem(shopItem: ShopItem)

    suspend fun getItemById(id: Int): ShopItem

    suspend fun editItem(shopItem: ShopItem)

    fun getShopList(): LiveData<List<ShopItem>>

}