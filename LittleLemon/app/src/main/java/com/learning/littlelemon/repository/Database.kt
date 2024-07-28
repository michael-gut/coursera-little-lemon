package com.learning.littlelemon.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MenuItemEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}
