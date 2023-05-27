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
