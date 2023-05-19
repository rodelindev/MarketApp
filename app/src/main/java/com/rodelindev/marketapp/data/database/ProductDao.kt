package com.rodelindev.marketapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rodelindev.marketapp.data.database.model.DBProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM table_product")
    fun getAllLocalProduct() : Flow<List<DBProduct>>

    @Query("SELECT *FROM table_product WHERE productId=:id")
    suspend fun findProductByUuid(id: String) : DBProduct

    @Update
    suspend fun updateProduct(dbProduct: DBProduct)

    @Delete
    suspend fun deleteProduct(dbProduct: DBProduct)

    @Query("DELETE FROM table_product" )
    suspend fun cleanShoppingCart()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(vararg dbProduct: DBProduct)

}