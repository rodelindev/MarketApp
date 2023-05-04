package com.rodelindev.marketapp.data.repositories

import com.rodelindev.marketapp.data.datasource.ProductDataSource
import javax.inject.Inject

class ProductRepository@Inject constructor(private val productDataSource: ProductDataSource){

    suspend fun populateProducts(idCategory:String) = productDataSource.populateProducts(idCategory)

}
