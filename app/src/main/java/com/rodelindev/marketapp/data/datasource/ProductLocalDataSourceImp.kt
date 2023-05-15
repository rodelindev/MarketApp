package com.rodelindev.marketapp.data.datasource

import com.rodelindev.marketapp.data.database.ProductDao
import com.rodelindev.marketapp.data.database.model.DBProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductLocalDataSourceImp @Inject constructor(
    private val productDao: ProductDao
) : ProductLocalDataSource {

    override fun getAllLocalProduct(): Flow<List<DBProduct>> = flow {
        try {
            productDao.getAllLocalProduct().collectLatest {
                emit(it)
            }
        } catch (ex: Exception) {
            println(ex.toString())
        }
    }

    override suspend fun findProductByUuid(id: String): DBProduct {
        return productDao.findProductByUuid(id)
    }

    override suspend fun updateProduct(dbProduct: DBProduct) {
        productDao.updateProduct(dbProduct)
    }

    override suspend fun deleteProduct(dbProduct: DBProduct) {
        productDao.deleteProduct(dbProduct)
    }

    override suspend fun saveProduct(dbProduct: DBProduct) {
        productDao.saveProduct(dbProduct)
    }
}