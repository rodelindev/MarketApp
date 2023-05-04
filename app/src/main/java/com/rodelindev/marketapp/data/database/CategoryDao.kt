package com.rodelindev.marketapp.data.database

import androidx.room.*
import com.rodelindev.marketapp.data.database.model.DBCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbCategory: List<DBCategory>)

    @Query("SELECT *FROM table_category")
    fun getAll() : Flow<List<DBCategory>>

    @Query("SELECT COUNT(uuid) from table_category")
    suspend fun counter():Int

    //Examples
    @Query("SELECT *FROM table_category WHERE uuid=:id")
    suspend fun findByUuid(id:String) : DBCategory

    @Update
    suspend fun update(dbCategory: DBCategory)

    @Delete
    suspend fun delete(dbCategory: DBCategory)

}