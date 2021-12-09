package com.neobis.shoplistcleanarchitecture.domain

class EditItemUseCase(private val repository: ShopListRepository) {
    fun editItem(shopItem: ShopItem){
        repository.editItem(shopItem)
    }
}