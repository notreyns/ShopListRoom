package com.neobis.shoplistcleanarchitecture.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    var isActive: Boolean,
    val counter: Int,
)
