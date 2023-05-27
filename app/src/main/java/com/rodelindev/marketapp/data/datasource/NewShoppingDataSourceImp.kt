package com.rodelindev.marketapp.data.datasource

import android.content.SharedPreferences
import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.data.remote.RemoteService
import com.rodelindev.marketapp.data.remote.model.ShippingRequest
import com.rodelindev.marketapp.data.remote.model.ShoppingDto
import com.rodelindev.marketapp.domain.model.toListCategory
import com.rodelindev.marketapp.domain.model.toUser
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class NewShoppingDataSourceImp @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val remoteService: RemoteService
) : NewShoppingDataSource {

    override suspend fun getNewShopping(request: ShippingRequest): Result<ShoppingDto> {
        return try {
            val token = sharedPreferences.getString("TOKEN", "") ?: ""
            val response = remoteService.getNewShopping("Bearer $token", request)
            Result.Success(data = response.body())
        } catch (ex: HttpException) {
            Result.Error(message = "Encontramos un error en su solicitud")
        } catch (ex: IOException) {
            Result.Error(message = "No se pudo conectar al servidor. Revise su conexion")
        }
    }
}