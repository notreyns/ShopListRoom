package com.neobis.shoplistcleanarchitecture.domain

class GetItemByIdUseCase(private val repository: ShopListRepository) {
    suspend fun getItemById(id: Int): ShopItem{
        return repository.getItemById(id)
    }
}