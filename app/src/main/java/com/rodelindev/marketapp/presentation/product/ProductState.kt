package com.rodelindev.marketapp.presentation.product

import com.rodelindev.marketapp.domain.model.Product

data class ProductState(
    val isLoading:Boolean=false,
    val error:String?=null,
    val success:List<Product>?=null
)
