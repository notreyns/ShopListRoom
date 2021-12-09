package com.neobis.shoplistcleanarchitecture.data

import com.neobis.shoplistcleanarchitecture.domain.ShopItem
import com.neobis.shoplistcleanarchitecture.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoId = 0

    override fun addItem(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoId
            autoId++
        }
        shopList.add(shopItem)
    }

    override fun deleteItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun getItemById(id: Int): ShopItem {
        return shopList
            .find{id == it.id }
            ?: throw RuntimeException("Element with id $id not found")
    }

    override fun editItem(shopItem: ShopItem) {
        val oldItem  = getItemById(shopItem.id)
        shopList.remove(oldItem)
        shopList.add(shopItem)
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}