package com.learning.littlelemon.dao

import kotlinx.serialization.Serializable

@Serializable
data class MenuCategory(val menu: List<String>)
