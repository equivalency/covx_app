package com.example.cov_x

import com.example.cov_x.models.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface UserService {
    @POST("auth/register")
     fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

     @POST("auth/login")
     fun loginUser(@Header("Authorization") authHeader: String, @Body loginRequest: LoginRequest): Call<LoginResponse>


     @POST("/upload")
     fun uploadImage(
      @Header("Authorization") authHeader: String,
      @Body file: RequestBody,
     ): Call<ResponseBody>

     @GET("/data")
     fun getFotoHasil(@Header("Authorization") authHeader: String): Call<PrediksiResponse>

     @DELETE("/data/{filename}")
     fun hapusDataPrediksi(@Header("Authorization") authHeader: String, @Path("filename") fileName: String): Call<ResponseBody>
}