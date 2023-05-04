package com.rodelindev.marketapp.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/*
object Api {

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("http://35.169.242.154:3000/")
        .addConverterFactory(GsonConverterFactory.create())


    interface RemoteService {

        @POST("api/usuarios/login")
        fun signIn(@Body request: LoginRequest): Response<LoginRemote>

    }

    fun build(): RemoteService {
        return builder.build().create(RemoteService::class.java)
    }
}*/
