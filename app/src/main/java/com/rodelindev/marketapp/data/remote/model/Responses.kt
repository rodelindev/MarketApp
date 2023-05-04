package com.rodelindev.marketapp.data.remote.model

import com.google.gson.annotations.SerializedName


//Login
data class LoginRemote(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("data")
    val data: UserRemote
)

data class UserRemote(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("nombres")
    val names: String,
    @SerializedName("apellidos")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("celular")
    val phone: String,
    @SerializedName("genero")
    val gender: String,
    @SerializedName("nroDoc")
    val document: String,
    @SerializedName("tipo")
    val type: String,
)

//Gender
data class GenderDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<GenderRemote>
)

data class GenderRemote(
    @SerializedName("genero")
    val gender: String,
    @SerializedName("descripcion")
    val description: String
)

//Categories
data class CategoryDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<CategoryRemote>
)

data class CategoryRemote(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("nombre")
    val name: String,
    @SerializedName("cover")
    val cover: String
)

//Product
data class ProductDto(
    @SerializedName("data")
    val data: List<ProductRemote>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)

data class ProductRemote(
    @SerializedName("cantidad")
    val quantity: Int,
    @SerializedName("caracteristicas")
    val features: String,
    @SerializedName("codigo")
    val code: String,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("imagenes")
    val images: List<String>,
    @SerializedName("precio")
    val price: Double,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("uuid")
    val uuid: String
)

//Shopping

data class ShoppingDto(
    val success: Boolean,
    val message: String,
    val data: String
)






