package com.neobis.shoplistcleanarchitecture.domain

class DeleteItemUseCase(private val repository: ShopListRepository) {
    suspend fun deleteItem(shopItem: ShopItem){
        repository.deleteItem(shopItem)
    }
}