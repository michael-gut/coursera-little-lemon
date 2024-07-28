package com.learning.littlelemon.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.littlelemon.utils.MenuCategories
import com.learning.littlelemon.utils.MenuItemNetwork
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String = "",
    val description: String = "",
    val price: String = "0.0",
    val image: String = "",
    val category: String = MenuCategories.Main.name,
)

fun convertToMenuItemEntity(dao: MenuItemNetwork): MenuItemEntity {
    return MenuItemEntity(
        id = dao.id,
        title = dao.title,
        description = dao.description,
        price = dao.price,
        image = dao.image,
        category = dao.category
    )
}
