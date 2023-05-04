package com.rodelindev.marketapp.data.datasource

import android.content.SharedPreferences
import com.rodelindev.marketapp.data.remote.RemoteService
import com.rodelindev.marketapp.domain.model.Product
import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.domain.model.toListProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductDataSourceImp @Inject constructor(private val remoteService: RemoteService, private val sharedPreferences: SharedPreferences) : ProductDataSource {
    override suspend fun populateProducts(categoryId: String): Flow<Result<List<Product>>> = flow {
        try{
            val token = sharedPreferences.getString("TOKEN","") ?: ""
            val response = remoteService.getProducts("Bearer $token",categoryId)
            emit(Result.Success(data =response.body()?.data?.toListProduct()))
        }catch (ex: HttpException){
            emit(Result.Error(message = "Hubo un problema al ejecutar su solicitud"))
        }catch (ex: IOException){
            emit(Result.Error(message = "No se pudo conectar al servidor, revise su conexion a internet"))
        }
    }
}
