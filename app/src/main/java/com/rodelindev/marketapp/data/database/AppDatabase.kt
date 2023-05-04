package com.rodelindev.marketapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodelindev.marketapp.data.database.model.DBCategory
import com.rodelindev.marketapp.data.database.model.DBProduct


@Database(entities = [DBCategory::class, DBProduct::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao() : CategoryDao
    abstract fun productDao() : ProductDao
}