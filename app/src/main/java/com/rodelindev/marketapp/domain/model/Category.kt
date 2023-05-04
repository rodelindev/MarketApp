package com.rodelindev.marketapp.domain.model

import com.google.gson.annotations.SerializedName
import com.rodelindev.marketapp.data.database.model.DBCategory
import com.rodelindev.marketapp.data.remote.model.CategoryRemote

data class Category(
    val uuid:String,
    val name:String,
    val cover:String
)

//List<CategoryRemote> -> List<Category>
fun List<CategoryRemote>.toListCategory() : List<Category> = map{
    Category(uuid = it.uuid, name = it.name, cover = it.cover)
}


/*fun List<DBCategory>.toListCategory() : List<Category> = map{
    Category(uuid = it.uuid, name = it.name, cover = it.cover)
}*/
