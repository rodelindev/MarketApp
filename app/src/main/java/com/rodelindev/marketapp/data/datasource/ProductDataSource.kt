package com.rodelindev.marketapp.data.datasource

import com.rodelindev.marketapp.domain.model.Product
import com.rodelindev.marketapp.data.Result
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {

    suspend fun populateProducts(categoryId:String) : Flow<Result<List<Product>>>
}
