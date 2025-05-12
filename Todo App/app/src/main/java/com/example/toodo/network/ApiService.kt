package com.example.toodo.network

import com.example.toodo.model.Livraison
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
        // Utilisation de JsonPlaceholder /todos pour tester CRUD
    @GET("todos")
    suspend fun getLivraisons(): List<Livraison>

    @POST("todos")
    suspend fun addLivraison(@Body livraison: Livraison): Livraison

    @PUT("todos/{id}")
    suspend fun updateLivraison(
        @Path("id") id: Int,
        @Body livraison: Livraison
    ): Livraison

    @DELETE("todos/{id}")
    suspend fun deleteLivraison(@Path("id") id: Int)
}