package com.rodelindev.marketapp.data.datasource

import com.rodelindev.marketapp.data.database.CategoryDao
import com.rodelindev.marketapp.data.database.model.DBCategory
import com.rodelindev.marketapp.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryLocalDataSourceImp @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryLocalDataSource {

    override suspend fun save(dbCategories: List<DBCategory>) {
        categoryDao.insert(dbCategories)
    }

    override suspend fun counter(): Int = categoryDao.counter()

    override val categories: Flow<List<Category>>
        get() = categoryDao.getAll().map {
            it.toListCategory()
        }
}

fun List<DBCategory>.toListCategory(): List<Category> = map {
    Category(uuid = it.uuid, name = it.name, cover = it.cover)
}
