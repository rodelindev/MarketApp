package com.rodelindev.marketapp.domain.model

import android.os.Parcelable
import com.rodelindev.marketapp.data.remote.model.ProductRemote
/*import kotlinx.android.parcel.Parcelize*/
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val quantity: Int,
    val features: String,
    val code: String,
    val description: String,
    val images: List<String>,
    val price: Double,
    val stock: Int,
    val uuid: String
) : Parcelable

//List<ProductRemote> -> List<Product>
fun List<ProductRemote>.toListProduct(): List<Product> = map {
    Product(
        quantity = it.quantity,
        features = it.features,
        code = it.code,
        description = it.description,
        images = it.images,
        price = it.price,
        stock = it.stock,
        uuid = it.uuid
    )
}
