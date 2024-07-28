package com.learning.littlelemon.utils

import kotlinx.serialization.Serializable

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Serializable
data class MenuNetwork(
    val menu: List<MenuItemNetwork>
)
