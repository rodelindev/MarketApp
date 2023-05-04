package com.rodelindev.marketapp.data.datasource

import com.rodelindev.marketapp.data.remote.RemoteService
import com.rodelindev.marketapp.domain.model.Gender
import com.rodelindev.marketapp.domain.model.toListGenders
import com.rodelindev.marketapp.data.Result
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GenderDataSourceImp @Inject constructor(
    private val remoteService: RemoteService
) : GenderDataSource {

    override suspend fun populateGenders(): Result<List<Gender>> {
        return try {
            val response = remoteService.getGenders()
            Result.Success(data = response.body()?.data?.toListGenders())
        } catch (ex: HttpException) {
            Result.Error(message = "Encontramos un error en tu solicitud")
        } catch (ex: IOException) {
            Result.Error(message = "No se pudo conectar al servidor, revise su conexion a internet")
        }
    }
}

