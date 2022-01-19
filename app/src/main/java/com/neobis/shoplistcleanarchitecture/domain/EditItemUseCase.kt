package com.neobis.shoplistcleanarchitecture.domain

class EditItemUseCase(private val repository: ShopListRepository) {
    suspend fun editItem(shopItem: ShopItem){
        repository.editItem(shopItem)
    }
}