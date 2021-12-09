package com.neobis.shoplistcleanarchitecture.domain

data class ShopItem(
    val name: String,
    var isActive: Boolean,
    val counter: Int,
    var id: Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = -1
    }
}
