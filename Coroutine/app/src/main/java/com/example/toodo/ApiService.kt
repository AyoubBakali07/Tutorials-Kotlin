package com.example.toodo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("products/1")
    fun getTodo():Call<todo>
}