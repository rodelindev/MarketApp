package com.rodelindev.marketapp.data.datasource

import android.content.SharedPreferences
import com.rodelindev.marketapp.data.remote.RemoteService
import com.rodelindev.marketapp.domain.model.Category
import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.domain.model.toListCategory
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CategoryDataSourceImp @Inject constructor(
    private val remoteService: RemoteService,
    private val sharedPreferences: SharedPreferences
) : CategoryDataSource {

    override suspend fun populateCategories(): Result<List<Category>> {
        return try {
            val token = sharedPreferences.getString("TOKEN", "") ?: ""
            val response = remoteService.getCategories("Bearer $token")
            Result.Success(data = response.body()?.data?.toListCategory())
        } catch (ex: HttpException) {
            if (ex.code() == 401) {
                Result.Error(message = "Su sesion ha expirado. Vuelva a ingresar")
            } else {
                Result.Error(message = "Encontramos un error en tu solicitud")
            }
        } catch (ex: IOException) {
            Result.Error(message = "No se pudo conectar al servidor, revise su conexion a internet")
        }
    }
}
