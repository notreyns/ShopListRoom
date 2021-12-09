package com.neobis.shoplistcleanarchitecture.domain

class DeleteItemUseCase(private val repository: ShopListRepository) {
    fun deleteItem(shopItem: ShopItem){
        repository.deleteItem(shopItem)
    }
}