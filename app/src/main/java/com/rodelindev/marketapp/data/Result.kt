package com.rodelindev.marketapp.data

sealed class Result<T>(val data:T?=null , val message:String?=null) {
    //Error, Success
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(message: String, data: T?=null) : Result<T>(data,message)
}