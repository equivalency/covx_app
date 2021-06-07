package com.example.cov_x

import com.example.cov_x.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("auth/register")
     fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

     @POST("auth/login")
     fun loginUser(@Header("Authorization") authHeader: String, @Body loginRequest: LoginRequest): Call<LoginResponse>

     @POST("/upload")
     fun uploadFoto(@Header("Authorization") authHeader: String, @Body fotoDataRequest: FotoDataRequest): Call<FotoDataResponse>

     @GET("/data")
     fun getFotoHasil(@Header("Authorization") authHeader: String, @Body id: Int): Call<ListFotoPrediksiResponse>
}