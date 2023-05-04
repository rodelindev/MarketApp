package com.rodelindev.marketapp.data.datasource

import com.rodelindev.marketapp.domain.model.Category
import com.rodelindev.marketapp.data.Result

interface CategoryDataSource {

    suspend fun populateCategories(): Result<List<Category>>

}