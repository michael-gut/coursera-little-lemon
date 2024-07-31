package com.learning.littlelemon.repository

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface MenuRepositoryInterface {
    suspend fun getAllItemsStream(): Flow<List<MenuItemEntity>>
    suspend fun getAllCategoriesStream(): Flow<List<String>>
    suspend fun getItemsStream(searchPhrase: String): Flow<List<MenuItemEntity>>
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

    override suspend fun getAllCategoriesStream(): Flow<List<String>> {
        return withContext(IO) {
            menuItemDao.getAllCategories()
        }
    }

    override suspend fun getItemsStream(searchPhrase: String): Flow<List<MenuItemEntity>> {
        return withContext(IO) {
            menuItemDao.getItemsForSearch(searchPhrase)
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
