package com.neobis.shoplistcleanarchitecture.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ShopListDao {
    @Query("SELECT * FROM shopitemdbmodel")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(shopItemDbModel: ShopItemDbModel)

    @Query("SELECT * FROM shopitemdbmodel where id=:shopItemId")
    suspend fun getShopItem(shopItemId: Int): ShopItemDbModel

    @Query("DELETE FROM shopitemdbmodel where id=:shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)

}