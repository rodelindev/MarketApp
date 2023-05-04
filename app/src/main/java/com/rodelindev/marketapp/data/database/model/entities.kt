package com.rodelindev.marketapp.data.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_category")
data class DBCategory(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    val uuid: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "cover")
    val cover: String,

    @ColumnInfo(name = "flat")
    val flat: Boolean
)


@Entity(tableName = "table_product")
data class DBProduct(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    val uuid: String,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "features")
    val features: String,

    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "images")
    val images: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "stock")
    val stock: Int,
)
/*
@Entity(tableName = "image_list")
data class DBImage(
    @ColumnInfo(name = "image")
    val images: String
)*/

