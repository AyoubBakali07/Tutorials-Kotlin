package com.example.livraison.network

import com.example.livraison.model.Livraison
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("travel/livraisons")
    suspend fun getAll(): List<livraison>
    @POST("travel/livraisons")
    suspend fun create(@Body livraison: Livraison): Livraison
    @PUT("travel/livraisons/{id}")
    suspend fun update(@Path("id") id: String, @Body livraison: Livraison): Livraison
    @DELETE("travel/livraisons/{id}")
    suspend fun delete(@Path("id") id: String): Response<Unit>
}