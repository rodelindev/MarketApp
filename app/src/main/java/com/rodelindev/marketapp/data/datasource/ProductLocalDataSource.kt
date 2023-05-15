package com.rodelindev.marketapp.data.datasource

import com.rodelindev.marketapp.data.database.model.DBProduct
import kotlinx.coroutines.flow.Flow

interface ProductLocalDataSource {

    fun getAllLocalProduct(): Flow<List<DBProduct>>

    suspend fun findProductByUuid(id: String): DBProduct

    suspend fun updateProduct(dbProduct: DBProduct)

    suspend fun deleteProduct(dbProduct: DBProduct)

    suspend fun saveProduct(dbProduct: DBProduct)
}