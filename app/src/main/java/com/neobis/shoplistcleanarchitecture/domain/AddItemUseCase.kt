package com.neobis.shoplistcleanarchitecture.domain

class AddItemUseCase(private val repository: ShopListRepository) {
    fun addItem(shopItem: ShopItem){
        repository.addItem(shopItem)
    }
}