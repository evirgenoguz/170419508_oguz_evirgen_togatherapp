package com.evirgenoguz.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.evirgenoguz.data.dao.UserDao
import com.evirgenoguz.data.model.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}