package com.rodelindev.marketapp.data.remote.model

import com.google.gson.annotations.SerializedName


data class LoginRequest(
    val email:String,
    val password:String,
    val firebaseToken:String=""
)

data class CreateAccountRequest(
    val nombres:String,
    val apellidos:String,
    val email:String,
    val password:String,
    val celular:String,
    val genero:String,
    val nroDoc:String,
    val firebaseToken:String=""
)

//Pedido
data class ShippingRequest(
    @SerializedName("direccionEnvio") val shippingAddress: ShippingAddress,
    @SerializedName("metodoPago") val payMethod: PayMethod,
    @SerializedName("fechaHora") val dateTime: String,
    @SerializedName("productos") val products: List<ProductRequest>,
    @SerializedName("total") val total: Int
)

data class ShippingAddress(
    @SerializedName("tipo") val type: Int,
    @SerializedName("direccion") val address: String,
    @SerializedName("referencia") val reference: String,
    @SerializedName("distrito") val district: String
)

data class PayMethod(
    @SerializedName("tipo") val typeMethod: Int,
    @SerializedName("monto") val amount: Int
)

data class ProductRequest(
    @SerializedName("categoriaId") val categoryId: String,
    @SerializedName("productoId") val productId: String,
    @SerializedName("cantidad") val productAmount: Int
)