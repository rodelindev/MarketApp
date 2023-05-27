package com.rodelindev.marketapp.data.repositories

import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.data.database.model.DBProduct
import com.rodelindev.marketapp.data.datasource.CategoryDataSource
import com.rodelindev.marketapp.data.datasource.CategoryLocalDataSource
import com.rodelindev.marketapp.data.datasource.NewShoppingDataSource
import com.rodelindev.marketapp.data.datasource.ProductLocalDataSource
import com.rodelindev.marketapp.data.remote.model.ShippingRequest
import com.rodelindev.marketapp.data.remote.model.ShoppingDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewShoppingRepository@Inject constructor(
    private val newShoppingDataSource: NewShoppingDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) {

    suspend fun getNewShopping(request: ShippingRequest): Result<ShoppingDto> = newShoppingDataSource.getNewShopping(request)

    fun getAllLocalProduct(): Flow<List<DBProduct>> = productLocalDataSource.getAllLocalProduct()

}