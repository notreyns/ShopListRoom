package com.neobis.shoplistcleanarchitecture.domain

interface ShopListRepository {
    fun addItem(shopItem: ShopItem)

    fun deleteItem(shopItem: ShopItem)

    fun getItemById(id: Int): ShopItem

    fun editItem(shopItem: ShopItem)

    fun getShopList(): List<ShopItem>

}