package com.rodelindev.marketapp.data.datasource

import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.data.remote.model.ShippingRequest
import com.rodelindev.marketapp.data.remote.model.ShoppingDto

interface NewShoppingDataSource {

    suspend fun getNewShopping(request: ShippingRequest): Result<ShoppingDto>

}