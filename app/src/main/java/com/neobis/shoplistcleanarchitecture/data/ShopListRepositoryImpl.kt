package com.neobis.shoplistcleanarchitecture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neobis.shoplistcleanarchitecture.domain.ShopItem
import com.neobis.shoplistcleanarchitecture.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {

    private var shopListLD= MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>( { p0, p1 ->p0.id.compareTo(p1.id) })

    init {
        for(i in 0 until 10){
            val item = ShopItem("Item $i",  true, i)
            addItem(item)
        }
    }

    private var autoId = 0

    override fun addItem(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoId
            autoId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun getItemById(id: Int): ShopItem {
        return shopList
            .find{it.id == id}
            ?: throw RuntimeException("Element with id $id not found")
    }

    override fun editItem(shopItem: ShopItem) {
        val oldItem  = getItemById(shopItem.id)
        shopList.remove(oldItem)
        addItem(shopItem)
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

   private fun updateList(){
        shopListLD.value = shopList.toList()
    }
}