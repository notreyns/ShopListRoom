package com.neobis.shoplistcleanarchitecture.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.neobis.shoplistcleanarchitecture.domain.ShopItem
import com.neobis.shoplistcleanarchitecture.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

    private val shopDao = AppDatabase.getInstance(application).shopListDao()

    private val mapper = ShopListMapper()
    override fun addItem(shopItem: ShopItem) {
        shopDao.addItem(mapper.mapEntityToDbModel(shopItem))

    }

    override fun deleteItem(shopItem: ShopItem) {
        shopDao.deleteShopItem(shopItem.id)
    }

    override fun getItemById(id: Int): ShopItem {
        return mapper.mapDbModelToEntity(shopDao.getShopItem(id))
    }

    override fun editItem(shopItem: ShopItem) {
        shopDao.addItem(mapper.mapEntityToDbModel(shopItem))
    }

    /*override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply {
        addSource(shopDao.getShopList()){
            mapper.mapListDbModelToEntityList(it)
        }
    }*/
    override fun getShopList(): LiveData<List<ShopItem>> =
        Transformations.map(
            shopDao.getShopList(),
            {
            mapper.mapListDbModelToEntityList(it)
            }
        )

}