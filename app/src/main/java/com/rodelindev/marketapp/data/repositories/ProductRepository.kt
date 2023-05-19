package com.rodelindev.marketapp.data.repositories

import com.rodelindev.marketapp.data.database.model.DBProduct
import com.rodelindev.marketapp.data.datasource.ProductDataSource
import com.rodelindev.marketapp.data.datasource.ProductLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) {

    suspend fun populateProducts(idCategory: String) = productDataSource.populateProducts(idCategory)

    fun getAllLocalProduct(): Flow<List<DBProduct>> = productLocalDataSource.getAllLocalProduct()

    suspend fun saveLocalProduct(dbProduct: DBProduct) {
        productLocalDataSource.saveProduct(dbProduct)
    }

    suspend fun deleteProduct(dbProduct: DBProduct) {
        productLocalDataSource.deleteProduct(dbProduct)
    }

    suspend fun cleanShoppingCart() {
        productLocalDataSource.cleanShoppingCarts()
    }
}

