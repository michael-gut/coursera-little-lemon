package com.learning.littlelemon.repository

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface MenuRepositoryInterface {
    suspend fun getAllItemsStream(): Flow<List<MenuItemEntity>>
    suspend fun getMenu(): Flow<Map<String, List<MenuItemEntity>>>
    suspend fun getItemStream(id: Int): Flow<MenuItemEntity?>
    suspend fun getMenuCategory(category: String): Flow<List<MenuItemEntity>>
    suspend fun insertMenuItem(item: MenuItemEntity)
    suspend fun deleteMenuItem(item: MenuItemEntity)
    suspend fun updateMenuItem(item: MenuItemEntity)
    suspend fun deleteAll()
}

class MenuRepository(
    private val menuItemDao: MenuItemDao
) : MenuRepositoryInterface {
    override suspend fun getAllItemsStream(): Flow<List<MenuItemEntity>> {
        return withContext(IO) {
            menuItemDao.getAllItems()
        }
    }

    override suspend fun getMenu(): Flow<Map<String, List<MenuItemEntity>>> {
        return withContext(IO) {
            menuItemDao.getAllItems().map { it.groupBy { it.category } }
        }
    }

    override suspend fun getItemStream(id: Int): Flow<MenuItemEntity?> {
        return withContext(IO) {
            menuItemDao.getMenuItem(id)
        }
    }

    override suspend fun getMenuCategory(category: String): Flow<List<MenuItemEntity>> {
        return withContext(IO) {
            menuItemDao.getItemsForCategory(category)
        }
    }

    override suspend fun insertMenuItem(item: MenuItemEntity) {
        println("Inserting $item")
        return withContext(IO) {
            menuItemDao.insert(item)
        }
    }

    override suspend fun deleteMenuItem(item: MenuItemEntity) {
        return withContext(IO) {
            menuItemDao.delete(item)
        }
    }

    override suspend fun updateMenuItem(item: MenuItemEntity) {
        return withContext(IO) {
            menuItemDao.update(item)
        }
    }

    override suspend fun deleteAll() {
        menuItemDao.deleteAll()
    }
}
