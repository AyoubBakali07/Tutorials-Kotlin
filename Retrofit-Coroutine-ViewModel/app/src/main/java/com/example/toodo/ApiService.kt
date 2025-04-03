package com.example.toodo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("todos/1")
    suspend fun getTodo(): Todo  // Using the corrected data class Todo
}
