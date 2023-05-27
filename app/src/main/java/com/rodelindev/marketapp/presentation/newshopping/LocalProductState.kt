package com.rodelindev.marketapp.presentation.newshopping

import com.rodelindev.marketapp.data.database.model.DBProduct

data class LocalProductState(
    val isLoading:Boolean=false,
    val error:String?=null,
    val products: List<DBProduct>? = null
)
