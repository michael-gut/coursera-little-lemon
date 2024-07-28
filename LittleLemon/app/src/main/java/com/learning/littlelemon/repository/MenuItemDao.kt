package com.learning.littlelemon.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

const val TABLE_NAME = "menu_items"

@Dao
interface MenuItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: MenuItemEntity)

    @Update
    suspend fun update(item: MenuItemEntity)

    @Delete
    suspend fun delete(item: MenuItemEntity)

    @Query("SELECT * from $TABLE_NAME WHERE id = :id")
    fun getMenuItem(id: Int): Flow<MenuItemEntity>

    @Query("SELECT * from $TABLE_NAME ORDER BY title ASC")
    fun getAllItems(): Flow<List<MenuItemEntity>>

    @Query("SELECT * from $TABLE_NAME WHERE category = :category ORDER BY title ASC")
    fun getItemsForCategory(category: String): Flow<List<MenuItemEntity>>

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()
}
