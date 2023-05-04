package com.rodelindev.marketapp.data.datasource

import android.content.SharedPreferences
import com.rodelindev.marketapp.data.remote.RemoteService
import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.data.remote.model.CreateAccountRequest
import com.rodelindev.marketapp.data.remote.model.LoginRequest
import com.rodelindev.marketapp.domain.model.User
import com.rodelindev.marketapp.domain.model.toUser
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserDataSourceImp @Inject constructor (
    private val sharedPreferences: SharedPreferences,
    private val remoteService: RemoteService
) : UserDataSource {

    override suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            /*val response = Api.build().signIn(LoginRequest(email, password))*/
            val response =  remoteService.signIn(LoginRequest(email, password))

            if(response.isSuccessful){
                if(response.body()?.success!!) {
                    response.body()?.token?.let {
                        sharedPreferences.edit().putString("TOKEN",it).apply()
                    }
                    Result.Success(data = response.body()?.data?.toUser())
                }else{
                    Result.Error(message = response.body()?.message!!)
                }
            }else{
                Result.Error(message = response.message())
            }

        } catch (ex: HttpException) {
            Result.Error(message = "Encontramos un error en su sollicitud")
        } catch (ex: IOException) {
            Result.Error(message = "No se pudo contactar con el servidor. revice su conexion")
        }
    }

    override suspend fun createAccount(createAccountRequest: CreateAccountRequest): Result<User> {

        return try{
            val response = remoteService.createAccount(createAccountRequest)
            if(response.isSuccessful){
                if(response.body()?.success!!){
                    response.body()?.token?.let {
                        sharedPreferences.edit().putString("TOKEN",it).apply()
                    }
                    Result.Success(data = response.body()?.data?.toUser())
                }else{
                    Result.Error(message = response.body()?.message!!)
                }
            }else{
                Result.Error(message = response.message())
            }

        }catch (ex:HttpException){
            Result.Error(message = "Encontramos un error en su solicitud")
        }catch (ex:IOException){
            Result.Error(message = "No se pudo conectar al servidor. Revise su conexion")
        }
    }
}