package com.rodelindev.marketapp.data.remote

import com.rodelindev.marketapp.data.remote.model.*
import com.rodelindev.marketapp.data.remote.model.CategoryDto
import com.rodelindev.marketapp.data.remote.model.CreateAccountRequest
import com.rodelindev.marketapp.data.remote.model.GenderDto
import com.rodelindev.marketapp.data.remote.model.LoginRemote
import com.rodelindev.marketapp.data.remote.model.LoginRequest
import com.rodelindev.marketapp.data.remote.model.ProductDto
import retrofit2.Response
import retrofit2.http.*

interface RemoteService {

    @POST("api/usuarios/login")
    suspend fun signIn(@Body request: LoginRequest): Response<LoginRemote>

    @GET("api/usuarios/obtener-generos")
    suspend fun getGenders(): Response<GenderDto>

    @POST("/api/usuarios/crear-cuenta")
    suspend fun createAccount(@Body request: CreateAccountRequest): Response<LoginRemote>

    @GET("api/categorias")
    suspend fun getCategories(@Header("Authorization") token: String): Response<CategoryDto>

    @GET("/api/categorias/{categoriaId}/productos")
    suspend fun getProducts(
        @Header("Authorization") token: String,
        @Path("categoriaId") categoryId: String
    ): Response<ProductDto>

    @POST("/api/compras/nueva-compra")
    suspend fun getNewShopping(
        @Header("Authorization") token: String,
        @Body request: ShippingRequest
    ): Response<ShoppingDto>
}