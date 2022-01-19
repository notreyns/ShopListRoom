package com.neobis.shoplistcleanarchitecture.domain

class AddItemUseCase(private val repository: ShopListRepository) {
    suspend fun addItem(shopItem: ShopItem){
        repository.addItem(shopItem)
    }
}