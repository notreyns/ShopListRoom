package com.neobis.shoplistcleanarchitecture.domain

class GetItemByIdUseCase(private val repository: ShopListRepository) {
    fun getItemById(id: Int): ShopItem{
        return repository.getItemById(id)
    }
}