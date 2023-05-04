package com.rodelindev.marketapp.data.repositories

import com.rodelindev.marketapp.data.database.model.DBCategory
import com.rodelindev.marketapp.data.datasource.CategoryDataSource
import com.rodelindev.marketapp.data.datasource.CategoryLocalDataSource
import com.rodelindev.marketapp.domain.model.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDataSource: CategoryDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource
) {

    val categories = categoryLocalDataSource.categories


    suspend fun requestCategory() {
        //1. Consulta el API
        val categoriesRemote = categoryDataSource.populateCategories()
        if (categoriesRemote.data != null) {

            //2. Comprobar Cantidad de categorias Api > Cantidad de categorias locales
            val counterCategoryRemote = categoriesRemote.data.size
            val counterCategoryLocal = categoryLocalDataSource.counter()

            if (counterCategoryRemote > counterCategoryLocal) {
                //3. Grabar la lista en la base de datos local
                categoryLocalDataSource.save(categoriesRemote.data.toListDBCategory())
            }
        }
    }
}

fun List<Category>.toListDBCategory(): List<DBCategory> = map {
    DBCategory(uuid = it.uuid, name = it.name, cover = it.cover, flat = true)
}

