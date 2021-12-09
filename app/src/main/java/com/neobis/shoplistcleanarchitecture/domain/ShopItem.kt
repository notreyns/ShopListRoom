package com.neobis.shoplistcleanarchitecture.domain

data class ShopItem(
    val id: Int,
    val name: String,
    val isActive: Boolean,
    val counter: Int
)
