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
    @PrimaryKey(autoGenerate = true)
    val proId: Int,

    @ColumnInfo(name = "productId")
    @NonNull
    val productId: String,

    @ColumnInfo(name = "categoryId")
    @NonNull
    val categoryId: String,

    @ColumnInfo(name = "images")
    val image: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "totalPay")
    val totalPay: Double,
)
/*
@Entity(tableName = "image_list")
data class DBImage(
    @ColumnInfo(name = "image")
    val images: String
)*/

