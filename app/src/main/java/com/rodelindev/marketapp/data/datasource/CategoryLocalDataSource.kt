package com.rodelindev.marketapp.data.datasource

import com.rodelindev.marketapp.data.database.model.DBCategory
import com.rodelindev.marketapp.domain.model.Category
import kotlinx.coroutines.flow.Flow


interface CategoryLocalDataSource {

    suspend fun save(dbCategories:List<DBCategory>)
    suspend fun counter():Int
    val categories : Flow<List<Category>>

}