package com.neobis.shoplistcleanarchitecture.data

import com.neobis.shoplistcleanarchitecture.domain.ShopItem

class ShopListMapper {
    fun mapDbModelToEntity(dbModel:ShopItemDbModel) = ShopItem(
        id = dbModel.id,
        name = dbModel.name,
        isActive = dbModel.isActive,
        counter = dbModel.counter
    )
    fun mapEntityToDbModel(entity:ShopItem) = ShopItemDbModel(
        id = entity.id,
        name = entity.name,
        isActive = entity.isActive,
        counter = entity.counter
    )

    fun mapListDbModelToEntityList(list: List<ShopItemDbModel>) = list.map{
        mapDbModelToEntity(it)
    }
}